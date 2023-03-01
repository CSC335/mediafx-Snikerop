package PlaySongDemo;

/**
 * This code will play any song assuming that file is in folder songfiles. 
 * 
 * Programmer Rick Mercer
 */
import java.io.File;
import java.net.URI;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayAnMP3 extends Application {
	MediaPlayer mediaPlayer;
	boolean pause = false;

	public static void main(String[] args) {
		launch(args);
	}

	private int songsPlayed = 0;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		HBox buttons = new HBox();
		//Changed the file path --Samuel
		String path = "songFiles/SwingCheese.mp3";
		pane.setCenter(new Label(path));
		//Added some buttons that have pause/play and stop functionality
		//--Samuel
		pane.setBottom(buttons);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(20);

		Button playPause = new Button("Pause");
		Button stop = new Button("Stop");

		playPause.setOnAction((event) -> {
			if (pause) {
				mediaPlayer.play();
				pause = false;
				playPause.setText("Pause");
			} else {
				mediaPlayer.pause();
				pause = true;
				playPause.setText("Play");
			}
		});
		stop.setOnAction((event) -> mediaPlayer.stop());
		buttons.getChildren().add(playPause);
		buttons.getChildren().add(stop);

		playASong(path);
		// Put the pane in a sized Scene and show the GUI
		Scene scene = new Scene(pane, 255, 85); // 255 pixels wide, 85 pixels tall
		stage.setScene(scene);
		// Don't forget to show the running app:
		stage.show();
	}

	private void playASong(String path) {

		// Need a File and URI object so the path works on all OSs
		File file = new File(path);
		URI uri = file.toURI();
		System.out.println(uri);
		// Play one mp3 and and have code run when the song ends
		Media media = new Media(uri.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();

		mediaPlayer.setOnEndOfMedia(new Waiter());
		mediaPlayer.setOnStopped(new Waiter());
		System.out.println("You may need to shut this App down");

	}

	private class Waiter implements Runnable {
		@Override
		public void run() {
			songsPlayed++;
			System.out.println("Song ended, play song #" + songsPlayed);
			Platform.exit();
		}
	}
}