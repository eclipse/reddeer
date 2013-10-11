package org.jboss.reddeer.junit.internal.screenrecorder;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_QUICKTIME_ANIMATION;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
/**
 * Extends ScreenRecorder class due to possibility to specify location
 * of file with recorder screen cast
 * @author Vlado Pakan
 * 
 * ScreenRecorder class was created by Werner Randelshofer, Immensee, Switzerland.
 * www.randelshofer.ch
 * 
 * Use of the Monte Media Library is free for all uses (non-commercial, commercial and educational)
 * under the terms of Creative Commons Attribution 3.0 (CC BY 3.0).
 * http://creativecommons.org/licenses/by/3.0/
 *
 */
public class ScreenRecorderExt extends ScreenRecorder{
  private String useFileName = null;
  public static final String STATE_DONE = "DONE";
  public static final String STATE_RECORDING = "RECORDING";
  public static final String STATE_FAILED = "FAILED";
  
  /**
   * Default constructor for .mov screen cast file
   * @throws IOException
   * @throws AWTException
   */
  public ScreenRecorderExt() throws IOException, AWTException {
    this(GraphicsEnvironment
          .getLocalGraphicsEnvironment()
          .getDefaultScreenDevice()
          .getDefaultConfiguration(),
        new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
              ENCODING_QUICKTIME_ANIMATION, CompressorNameKey,
              ENCODING_QUICKTIME_ANIMATION, DepthKey, (int) 24,
              FrameRateKey, Rational.valueOf(30), QualityKey, 1.0f,
              KeyFrameIntervalKey, (int) 1),
        new Format(MediaTypeKey,
              MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
              Rational.valueOf(30)),
        null);
  }  
  /**
   * Creates ScreenRecorderExt instance with parametrs
   * @param cfg
   * @param fileFormat
   * @param screenFormat
   * @param mouseFormat
   * @param audioFormat
   * @throws IOException
   * @throws AWTException
   */
  public ScreenRecorderExt(GraphicsConfiguration cfg, Format fileFormat,
      Format screenFormat, Format mouseFormat, Format audioFormat)
      throws IOException, AWTException {
    super(cfg, fileFormat, screenFormat, mouseFormat, audioFormat);
  }

  /**
   * Start screen cast recording to specified file name 
   * @param fileName
   * @throws IOException
   */
  public void start(String fileName) throws IOException{
    this.useFileName = fileName;
    super.start();
  }
  /**
   * Start screen cast recording to default file name 
   * @throws IOException
   */
  @Override
  public void start() throws IOException{
    this.useFileName = null;
    super.start();
  }
  /**
   * Create movie file for recorder screen cast. Screen cast will be stored
   * to file specified within proceeding start() method invocation
   * 
   */
  @Override
  protected File createMovieFile(Format fileFormat) throws IOException {
    File fileToWriteMovie;
    if (this.useFileName == null || this.useFileName.length() == 0){
      fileToWriteMovie = super.createMovieFile(fileFormat);
    }
    else {
      if (this.useFileName.indexOf('.') < 0){
        this.useFileName += '.' + Registry.getInstance().getExtension(fileFormat);
      }
      fileToWriteMovie = new File(this.useFileName);
      if (fileToWriteMovie.exists()){
        fileToWriteMovie.delete();
      }
    }
    
    return fileToWriteMovie;
  }
  
  /**
   * Returns true if state of Screen Recorder is as specified by parameter state
   * @param state
   * @return
   */
  public boolean isState(String state) {
    boolean result = false;
    
    if (state.equals(ScreenRecorderExt.STATE_DONE) && getState().equals(State.DONE)){
      result = true;
    } else if (state.equals(ScreenRecorderExt.STATE_RECORDING) && getState().equals(State.RECORDING)){
      result = true;
    } else if (state.equals(ScreenRecorderExt.STATE_RECORDING) && getState().equals(State.RECORDING)){
      result = true;
    } else if (state.equals(ScreenRecorderExt.STATE_FAILED) && getState().equals(State.FAILED)){
      result = true;
    }    
    
    return result;
  }
  /**
   * Overrides parent definition.
   * Defined to not have access restriction warning
   */
  @Override
  public void stop() throws IOException{
    super.stop();
  }

}