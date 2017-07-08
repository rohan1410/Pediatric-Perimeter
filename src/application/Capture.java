package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.time.*;
import java.time.temporal.ChronoField;
import java.awt.*;
import java.text.*;
import com.github.sarxos.webcam.Webcam;

public class Capture extends Setup{
 	ImageView imgWebcamCapturedImage,protractor;
	HBox webcamPane,root;
	ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	String path3 = workingDirectory + "protractor.png";
	LocalDateTime now = LocalDateTime.now();
	
	public Capture()
	{
		webcamPane = new HBox();
		webcamPane.setPadding(new Insets(90,60,110,150));//top, left, bottom, right
		webcamPane.setSpacing(30);
		imgWebcamCapturedImage = new ImageView();
//		System.out.println("1");
		Image img1 = new Image("images/protractor.png");
		protractor = new ImageView(img1);
//		System.out.println(path3);
		imgWebcamCapturedImage.setFitHeight(100);
		imgWebcamCapturedImage.setFitWidth(100);
		imgWebcamCapturedImage.prefHeight(100);
		imgWebcamCapturedImage.prefWidth(100);
		imgWebcamCapturedImage.setPreserveRatio(true);
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imgWebcamCapturedImage, protractor);
		
		webcamPane.setPrefSize(640,480);
		webcamPane.getChildren().add(stackPane);
	}
	
	public void capture_now(Group g)
	{
		initWebcam(0);
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				setImageViewSize();
			}
		});
		g.getChildren().add(webcamPane);
	}
	
	protected void initWebcam(final int index)
	{
		Task<Void> webCamTask = new Task<Void>()
		{
			protected Void call() throws Exception
			{
				if(webcam != null)
				{
					System.out.println("Not found");
				}
				webcam = Webcam.getWebcams().get(index);
				webcam.open();
				startWebcam();
				return null;
			}
		};
		
		Thread webCamThread = new Thread(webCamTask);
		webCamThread.setDaemon(true);
		webCamThread.start();

		//bottomCameraControlPane.setDisable(false);
		//btnCamreaStart.setDisable(true);
	}
	
	protected void startWebcam()
	{
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				initialMillis = now.get(ChronoField.MILLI_OF_SECOND);
				final AtomicReference<WritableImage> ref = new AtomicReference<>();
				BufferedImage img = null;
				int i=0;
				while (true) {
					try {
						if ((img = webcam.getImage()) != null) {

							String name="test"+i+".png";
							ImageIO.write(img, "PNG", new File(name));
							ref.set(SwingFXUtils.toFXImage(img, ref.get()));
							img.flush();

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									imageProperty.set(ref.get());
								}
							});
						}
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//return null;
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebcamCapturedImage.imageProperty().bind(imageProperty);
	}
	
	protected void setImageViewSize() {

		double height = webcamPane.getHeight();
		double width = webcamPane.getWidth();
        System.out.println("Height "+height+"Width "+width);
		imgWebcamCapturedImage.setFitHeight(height+640);
		imgWebcamCapturedImage.setFitWidth(width+480);
		imgWebcamCapturedImage.prefHeight(height+640);
		imgWebcamCapturedImage.prefWidth(width+480);
		imgWebcamCapturedImage.setPreserveRatio(true);

	}

}
