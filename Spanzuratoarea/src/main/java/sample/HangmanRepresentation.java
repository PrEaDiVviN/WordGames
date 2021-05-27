package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class HangmanRepresentation extends Parent {
    private static final int SPINE_START_X = 100;
    private static final int SPINE_START_Y = 20;
    private static final int SPINE_END_X = SPINE_START_X;
    private static final int SPINE_END_Y = SPINE_START_Y + 50;

    public SimpleIntegerProperty lives = new SimpleIntegerProperty();

    public HangmanRepresentation() {
        Circle head = new Circle(20);
        head.setTranslateX(SPINE_START_X);

        Line spine = new Line();
        spine.setStartX(SPINE_START_X);
        spine.setStartY(SPINE_START_Y);
        spine.setEndX(SPINE_END_X);
        spine.setEndY(SPINE_END_Y);

        Line leftArm = new Line();
        leftArm.setStartX(SPINE_START_X);
        leftArm.setStartY(SPINE_START_Y);
        leftArm.setEndX(SPINE_START_X + 40);
        leftArm.setEndY(SPINE_START_Y + 10);

        Line rightArm = new Line();
        rightArm.setStartX(SPINE_START_X);
        rightArm.setStartY(SPINE_START_Y);
        rightArm.setEndX(SPINE_START_X - 40);
        rightArm.setEndY(SPINE_START_Y + 10);

        Line leftLeg = new Line();
        leftLeg.setStartX(SPINE_END_X);
        leftLeg.setStartY(SPINE_END_Y);
        leftLeg.setEndX(SPINE_END_X + 25);
        leftLeg.setEndY(SPINE_END_Y + 50);

        Line rightLeg = new Line();
        rightLeg.setStartX(SPINE_END_X);
        rightLeg.setStartY(SPINE_END_Y);
        rightLeg.setEndX(SPINE_END_X - 25);
        rightLeg.setEndY(SPINE_END_Y + 50);

        getChildren().addAll(head, spine, leftArm, rightArm, leftLeg, rightLeg);
        lives.set(getChildren().size());
    }

    public void reset() {
        getChildren().forEach(node -> node.setVisible(false));
        lives.set(getChildren().size());
    }

    public void takeAwayLife() {
        for (Node n : getChildren()) {
            if (!n.isVisible()) {
                n.setVisible(true);
                lives.set(lives.get() - 1);
                break;
            }
        }
    }
}