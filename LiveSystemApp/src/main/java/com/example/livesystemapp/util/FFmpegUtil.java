package com.example.livesystemapp.util;

import com.example.livesystemapp.util.Param.GlobalParam;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.librealsense.frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FFmpegUtil {

    /**
     * 日志组件
     */
    private static final Logger log = LoggerFactory.getLogger(FFmpegUtil.class);

    /**
     * FFmpeg
     */
    public static ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();

    /**
     * 获取电脑设备(摄像头Video与麦克风Audio)
     *
     * @return
     */
    public Map<String,List<String>> getDevice() {
        List<String> deviceList = new ArrayList<>();
        Map<String,List<String>> device = new HashMap<>();
        try {
            ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-list_devices");
            ffmpeg.addArgument("true");
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("dshow");
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument("dummy");
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                String line = br.readLine();
                if(line.indexOf("DirectShow video devices") != -1) {
                    String str = br.readLine();
                    while(line.indexOf("DirectShow audio devices") == -1 && line != null &&line.indexOf("Immediate exit requested") == -1) {
                        if(line.indexOf("Alternative name") != -1) {
                            line = br.readLine();
                            str = line;
                            continue;
                        }
                        deviceList.add(str.substring(str.indexOf("\"")+1,str.lastIndexOf("\"")));
                        line = br.readLine();
                    }
                }
                device.put("Video",deviceList);
                deviceList = new ArrayList<>();
                if(line.indexOf("DirectShow audio devices") != -1) {
                    String str = br.readLine();
                    while(line.indexOf("DirectShow video devices") == -1 && line != null && line.indexOf("Immediate exit requested") == -1) {
                        if(line.indexOf("Alternative name") != -1) {
                            line = br.readLine();
                            str = line;
                            continue;
                        }
                        deviceList.add(str.substring(str.indexOf("\"")+1,str.lastIndexOf("\"")));
                        line = br.readLine();
                    }

                }
                device.put("Audio",deviceList);
            }
        } catch (IOException e) {
            log.error(""+e);
        }
        return device;
    }

    private static CanvasFrame grabberCanvas = null;

    /**
     * 启动摄像头
     *
     * @param device
     */
    public void startDevice(int device) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(device);//新建opencv抓取器，一般的电脑和移动端设备中摄像头默认序号是0，不排除其他情况
        try {
            grabber.start();//开始获取摄像头数据
            CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个预览窗口
            grabberCanvas = canvas;
            canvas.setAlwaysOnTop(true);
            canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //窗口是否关闭
            while(canvas.isDisplayable()){
                /*获取摄像头图像并在窗口中显示,这里Frame frame=grabber.grab()得到是解码后的视频图像*/
                canvas.showImage(grabber.grab());
            }
            grabber.close();
        } catch (FrameGrabber.Exception e) {
            log.error("startDevice:"+e);
        }
    }

    /**
     * 关闭摄像头
     */
    public void stopDevice() {
        if(grabberCanvas != null) {
            grabberCanvas.dispose();
            grabberCanvas = null;
        }
    }

    /**
     * 开启直播，如果打开摄像头不需要额外推流，只需要注意音频信息
     *
     * @param mikeDev
     */
    public void statLive(Integer uid, String mikeDev) {
        try {
            ffmpeg = live_Record(mikeDev);
            ffmpeg.addArgument(GlobalParam.liveLocation+uid);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFFmpeg(br);
            }
        } catch (IOException e) {
            log.error(""+e);
        }
    }

   public void stopLive() {
        try {
            ffmpeg.close();
            ffmpeg.destroy();
        } catch (NullPointerException e) {
            log.error("直播进程为出现问题："+e);
        }
   }

    private static Thread liveThread; //直播线程

    /**
     * 观看直播
     */
    public void liveView(String filepath) {
        Thread playThread = new Thread(() -> {
            try {
                //开始抓取
                @SuppressWarnings("resource")
                final FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(GlobalParam.liveLocation+filepath);
                grabber.start();
                //初始化视图
                CanvasFrame canvas = new CanvasFrame("直播间",CanvasFrame.getDefaultGamma() / grabber.getGamma());//新建一个窗口
                canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //初始化扬声器
                final AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                final SourceDataLine soundLine;
                if(info.getMaxBufferSize() < info.getMinBufferSize() || info.getMaxBufferSize() == info.getMinBufferSize()) {
                    soundLine = null;
                } else {
                    soundLine = (SourceDataLine) AudioSystem.getLine(info);
                }
                if(soundLine != null) {
                    soundLine.open(audioFormat);
                    soundLine.start();
                }
                //图像转换器 将帧中的image相关信息提取出来
                final Java2DFrameConverter converter = new Java2DFrameConverter();

                ExecutorService executor = Executors.newSingleThreadExecutor();

                while (!Thread.interrupted()) {
                    Frame frame = grabber.grab();
                    if (frame == null) {
                        break;
                    }
                    //帧 为图像帧 或 声音帧
                    if (frame.image != null) {
                        Image image = converter.convert(frame);
                        new Runnable() { public void run() {
                            canvas.showImage(image);
                        }}.run();;
                    } else if (frame.samples != null) {
                        final ShortBuffer channelSamplesShortBuffer = (ShortBuffer) frame.samples[0];
                        channelSamplesShortBuffer.rewind();

                        final ByteBuffer outBuffer = ByteBuffer.allocate(channelSamplesShortBuffer.capacity() * 2);

                        for (int i = 0; i < channelSamplesShortBuffer.capacity(); i++) {
                            short val = channelSamplesShortBuffer.get(i);
                            outBuffer.putShort(val);
                        }
                        /*
                         * 写入到扬声器
                         *   并准备下一次读取
                         */
                        try {
                            executor.submit(() -> {
                                if(soundLine != null) {
                                    soundLine.write(outBuffer.array(), 0, outBuffer.capacity());
                                }
                                outBuffer.clear();
                            }).get();
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                executor.shutdownNow();
                executor.awaitTermination(10, TimeUnit.SECONDS);
                if(soundLine != null) {
                    soundLine.stop();
                }
                grabber.stop();
                grabber.release();
            } catch (Exception exception) {
                log.error("拉流出错："+exception);
            }
        });
        liveThread = playThread;
        playThread.start();
    }

    /**
     * 停止观看直播
     */
    public void stopLiveView() {
        try {
            liveThread.stop();
            GlobalParam.LiveGroup = null;
        } catch (NullPointerException e) {
            log.error("观看直播进程为空："+e);
        }
    }

    private static FFmpegFrameGrabber grabber = null;
    private static FFmpegFrameRecorder recorder = null;

    /**
     * 保存直播视频
     *
     * @param fileName
     * @param rtmpLocation
     */
    public void RecordVideo(String fileName, String rtmpLocation) {
        // 获取视频源
        grabber = new FFmpegFrameGrabber(GlobalParam.liveLocation+rtmpLocation);
        recorder = null;
        try {
            grabber.start();
            Frame frame = grabber.grabFrame();
            if (frame != null) {
                if(fileName == null) {
                    fileName = "D:/直播录屏"+new Date().getTime();
                }
                File outFile = new File(fileName);
                if (!outFile.isFile()) {
                    try {
                        outFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
                recorder = new FFmpegFrameRecorder(fileName, 1080, 1440, 1);
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// 直播流格式
                recorder.setFormat("flv");// 录制的视频格式
                recorder.setFrameRate(25);// 帧数
                //百度翻译的比特率，默认400000，但是我400000贼模糊，调成800000比较合适
                recorder.setVideoBitrate(800000);
                recorder.start();
                while ((frame != null)) {
                    recorder.record(frame);// 录制
                    frame = grabber.grabFrame();// 获取下一帧
                }
                recorder.record(frame);
                // 停止录制
                recorder.stop();
                grabber.stop();
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            if (null != grabber) {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 停止直播录制
     */
    public void stopRecordVideo() {
        try {
            // 停止录制
            recorder.stop();
            grabber.stop();
        } catch (Exception e) {
            log.error("停止直播录制错误："+e);
        }
    }

    private static ProcessWrapper ffmpegRecord = new DefaultFFMPEGLocator().createExecutor();

    @Async("asyncServiceExecutor")
    public void recording(String locationTo, String mikeDev) {
        try {
            ffmpegRecord = live_Record(mikeDev);
            if(locationTo != null) {
                ffmpegRecord.addArgument(locationTo+"/录制"+new Date().getTime()+".flv");
            } else {
                ffmpegRecord.addArgument("D:/录制"+new Date().getTime()+".flv");
            }
            ffmpegRecord.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpegRecord.getErrorStream()))) {
                blockFFmpeg(br);
            }
        } catch (IOException e) {
            log.error(""+e);
        }
    }

    public void stopRecord() {
        try {
            ffmpegRecord.destroy();
        } catch (NullPointerException e) {
            log.error("停止课程录制错误："+e);
        }
    }

    /**
     * 推流与录像的命令只有最后的地址不一样，推流是网址，录像是文件
     *
     * @param mikeDev
     */
    private ProcessWrapper live_Record(String mikeDev) {
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-f");
        ffmpeg.addArgument("gdigrab");
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument("desktop");
        ffmpeg.addArgument("-f");
        ffmpeg.addArgument("dshow");
        if(mikeDev != null) {
            ffmpeg.addArgument("-i");
//            try {
//                byte[] str = mikeDev.getBytes("utf-8");
//                ffmpeg.addArgument("audio=\""+str.toString()+"\"");
//            } catch (UnsupportedEncodingException e) {
//                log.error("转码错误:"+e);
//            }
            ffmpeg.addArgument("audio=\""+mikeDev.trim()+"\"");
        }
        ffmpeg.addArgument("-b");
        ffmpeg.addArgument("5M");
        ffmpeg.addArgument("-pix_fmt");
        ffmpeg.addArgument("yuv420p");
        ffmpeg.addArgument("-vcodec");
        ffmpeg.addArgument("libx264");
        ffmpeg.addArgument("-s");
        ffmpeg.addArgument("1280x720");
        ffmpeg.addArgument("-r");
        ffmpeg.addArgument("25");
        ffmpeg.addArgument("-f");
        ffmpeg.addArgument("flv");
        return ffmpeg;
    }

    /**
     * 等待命令执行成功，退出
     *
     * @param br
     * @throws IOException
     */
    private void blockFFmpeg(BufferedReader br) throws IOException {
        String line;
        // 该方法阻塞线程，直至合成成功
        while ((line = br.readLine()) != null) {
            doNothing(line);
        }
    }

    /**
     * 打印日志，调试阶段可解开注释，观察执行情况
     *
     * @param line
     */
    private static void doNothing(String line) {
        log.info(line);
    }
}
