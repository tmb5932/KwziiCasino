import javafx.application.Application;
import javafx.stage.Stage;

public class CasinoGUI extends Application implements Observer<CasinoModel, String> {
    private CasinoModel model;
    /**
     * In the init the GUI creates the model and adds itself as an observer
     * of it.
     */
    @Override
    public void init() {
        this.model = new CasinoModel();
        model.addObserver(this);
        System.out.println("init: Initialize and connect to model!");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void update(CasinoModel casinoModel, String s) {

    }


}
