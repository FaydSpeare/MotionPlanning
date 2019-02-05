import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserPanel extends VBox {

    private ComboBox strategyBox;
    private ComboBox obstacleSet;

    private enum Strategy {
        PRM,
        RRT,
        RRTstar;
    }

    private Strategy strategy;
    private MotionSpace space;

    public UserPanel(MotionSpace space){
        super();

        this.space = space;
        setup();
    }

    private void setup(){

        HBox addition = new HBox();
        Button addOne = new Button("Add 1 Point");
        addOne.setOnMouseClicked(event -> {
            if(strategy == Strategy.PRM) space.addPRM(1);
            else if(strategy == Strategy.RRT) space.addRRT(1);
            else if(strategy == Strategy.RRTstar) space.addRRTStar(1);
        });

        Button addTwo = new Button("Add 50 Points");
        addTwo.setOnMouseClicked(event -> {
            if(strategy == Strategy.PRM) space.addPRM(50);
            else if(strategy == Strategy.RRT) space.addRRT(50);
            else if(strategy == Strategy.RRTstar) space.addRRTStar(50);
        });

        Button addThree = new Button("Add 500 Points");
        addThree.setOnMouseClicked(event -> {
            if(strategy == Strategy.PRM) space.addPRM(500);
            else if(strategy == Strategy.RRT) space.addRRT(500);
            else if(strategy == Strategy.RRTstar) space.addRRTStar(500);
        });

        Button connect = new Button("Connect PRM");
        connect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                space.connect();
            }
        });

        addition.getChildren().addAll(addOne, addTwo, addThree, connect);
        addition.setSpacing(5);

        Button clear = new Button("Clear Space");
        clear.setOnMouseClicked(event -> {
            space.reset();
        });

        strategyBox = new ComboBox();
        strategyBox.setPromptText("Search Strategy");
        strategyBox.setOnAction(this::updateStrategy);
        strategyBox.getItems().addAll(
                "Probabilistic Road Map",
                "Rapidly Expanding Random Tree",
                "Rapidly Expanding Random Tree Star"
        );

        obstacleSet = new ComboBox();
        obstacleSet.setPromptText("Obstacle Set");
        obstacleSet.setOnAction(this::updateObstacles);
        obstacleSet.getItems().addAll(
                "Set 1",
                "Set 2",
                "Set 3",
                "No Obstacles"
        );

        HBox slider = new HBox();

        Label label = new Label("RRT Increment:  ");

        Slider multiplierSlider = new Slider();
        multiplierSlider.setMin(0);
        multiplierSlider.setMax(30);
        multiplierSlider.setValue(10);
        multiplierSlider.setShowTickLabels(true);
        multiplierSlider.setShowTickMarks(true);
        multiplierSlider.setMajorTickUnit(2);
        multiplierSlider.setMinorTickCount(1);
        multiplierSlider.setBlockIncrement(10);
        multiplierSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            space.setRRTMultiplier(newValue.intValue());
        });
        multiplierSlider.setPrefWidth(300);
        slider.getChildren().addAll(label, multiplierSlider);

        this.getChildren().addAll(addition, clear, strategyBox, obstacleSet, slider);


        this.setSpacing(5);
        this.setPadding(new Insets(10));
    }

    private void updateObstacles(Event event) {
        if(obstacleSet.getValue().equals("Set 1")) space.setObstacles(0);
        else if(obstacleSet.getValue().equals("Set 2")) space.setObstacles(1);
        else if(obstacleSet.getValue().equals("Set 3")) space.setObstacles(2);
        else if(obstacleSet.getValue().equals("No Obstacles")) space.setObstacles(3);
    }


    private void updateStrategy(Event event) {
        if(strategyBox.getValue().equals("Probabilistic Road Map")) strategy = Strategy.PRM;
        else if(strategyBox.getValue().equals("Rapidly Expanding Random Tree")) strategy = Strategy.RRT;
        else if(strategyBox.getValue().equals("Rapidly Expanding Random Tree Star")) strategy = Strategy.RRTstar;
    }

}

