package sample;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Letter extends StackPane {
    private Rectangle box = new Rectangle(40, 60);
    private Text text;

    public Letter(char letter) {
        box.setFill(letter == ' ' ? Color.DARKBLUE : Color.BEIGE);
        box.setStroke(Color.CORAL);

        text = new Text(String.valueOf(letter).toUpperCase());
        text.setVisible(false);

        setAlignment(Pos.CENTER);
        getChildren().addAll(box, text);
    }

    public void show() {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), box);
        rt.setAxis(Rotate.Y_AXIS);
        rt.setToAngle(180);
        rt.setOnFinished(event -> text.setVisible(true));
        rt.play();
    }

    public boolean isEqualTo(char other) {
        return text.getText().equals(String.valueOf(other).toUpperCase());
    }
}
