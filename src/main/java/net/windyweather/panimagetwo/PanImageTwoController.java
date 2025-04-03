package net.windyweather.panimagetwo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PanImageTwoController {
    public ScrollPane spScrollPane;
    public ImageView imgImageView;
    public CheckBox cbSlideToZoom;
    public Slider sbSlideToZoom;
    public Label lblSliderValue;
    public Spinner<Double> spinScrollPaneAdjust;
    public Label lblStatus;
    public Button btnHello;
    public Button btnOpenImage;
    @FXML
    private Label welcomeText;

    @FXML

    public Image anImage;
    private double dZoomScale;

    @FXML
    protected void onHelloButtonClick() {
        lblStatus.setText("Does this program work?");
    }

    protected void setStatus( String sts ) {
        lblStatus.setText(sts);
    }

    public static void printSysOut( String str ) {
        System.out.println(str);
    }


    /*
        Initialize things in the controller for the GUI
     */
    public void SetUpStuff() {

    }


    /*
        Open a test image starting in our local folders
        But you can go get any image you want
     */

    public void onOpenImageClick(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        /*
            get stage to use as parent for dialog
            and set a default path for the file chooser
         */
        //File defFile = new File("D:\\MMO_Pictures\\AlienBlackout\\2025_03");
        String sDirExamples = System.getProperty("user.dir") + "\\testImages";

        File defFile = new File(sDirExamples);
        fileChooser.setInitialDirectory(defFile);
        fileChooser.setTitle("Open a Test Image");
        Stage stage = (Stage) btnOpenImage.getScene().getWindow();
        File selectedImageFile = null;
        /*
            Keep trying until cancel or we get a path
         */
        int errorCount = 0;
        while (true) {
            try {
                selectedImageFile = fileChooser.showOpenDialog(stage);
            } catch (Exception e) {
                /*
                 We can ignore the exception and just continue because
                 we initialized selectedImageFile with null
                 */
                if ( errorCount > 2 ){
                    break;
                }
                setStatus("Invalid starting file path for fileChooser");
                printSysOut("Invalid starting file path for fileChooser");
                /*
                    Go back around with a default path and let the
                    user find an image.
                 */
                File aFile = new File("C:\\");
                fileChooser.setInitialDirectory(aFile);
                errorCount++;
                continue;
            }
            if (selectedImageFile == null) {
                setStatus("No Image Chosen");
                return;
            } else {
                break;
            }
        }

        /*
            we appear to have a file so we will try to load the image with it.
         */
        InputStream imageAsStream = null;
        try {
            assert selectedImageFile != null;
            imageAsStream = new FileInputStream(selectedImageFile);
        } catch (FileNotFoundException e) {
            printSysOut("Somehow, image file not found");
            throw new RuntimeException(e);
        }

        /*
            Stuff from PanImageTest - don't do that here
         */
        if ( false ) {
        /*
            Request the image be loaded much larger than it probably is
            to make panning in the viewport work.
         */
        /*
            Only way I see to get the size is to read it,
            and then read it again to request it with a larger size
         */
            Image imageForSize = new Image(imageAsStream);
            double dWforSize = imageForSize.getWidth();
            double dHforSize = imageForSize.getHeight();
            double dSizeFactor = 2.0;

            printSysOut(String.format("OpenImageClick Image Size 1st time [%.0f, %.0f]", dWforSize, dHforSize));
            try {
                imageAsStream = new FileInputStream(selectedImageFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            anImage = new Image(imageAsStream, dWforSize * dSizeFactor, dHforSize * dSizeFactor, true, true);
            double dWidth = anImage.getWidth();
            double dHeight = anImage.getHeight();

            printSysOut(String.format("OpenImageClick Image Size 2nd time [%.0f, %.0f]", dWidth, dHeight));

            imgImageView.setPreserveRatio(true);
            imgImageView.setSmooth(true);
            imgImageView.setImage(anImage);
            imgImageView.setFitWidth(dWidth);
            //imgImageView.setFitHeight( dHeight );


       /*
            Set scale factors due to "RequestedSize" above.
            and update GUI to show values
         */
            double dScale = 0.5;
            imgImageView.setScaleX(dScale);
            lblSliderValue.setText(String.format("Zoom %.4f", dScale));
            sbSlideToZoom.setValue(dScale);

        /*
            Set the zoom stuff in the GUI to show us we've reduced the size
         */

        /*
            Did we forget to do this?
            NO! IT MAKES NO F****** DIFFERENCE
         */
            //SetImageViewViewport();
            if (false) {
                Rectangle2D viewportRect = new Rectangle2D(0, 0, dWidth * 2.0, dHeight * 2.0);
                imgImageView.setViewport(viewportRect);
            }

            printSysOut(String.format("image displayed [%.0f, %.0f]", dWidth, dHeight));

            //AdjustScrollPane();

            setStatus(String.format("image displayed [%.0f, %.0f]", dWidth, dHeight));
        } // PanImageTest things we are not doing now.

        /*
            Do only what PanZoomImage does here
            at this point we have imageAsStream
         */

        anImage = new Image( imageAsStream );
        imgImageView.setImage( anImage );

        imgImageView.setPreserveRatio(true);

        /*
            Don't need the following because the ScrollPane is already connected
            to the imageview by fxml
         */
        //spScrollPane = new ScrollPane(imgImageView);
        spScrollPane.setPannable(true);
        spScrollPane.setHvalue(0.5);
        spScrollPane.setVvalue(0.5);
        /*
            Min, max, default
         */
        //sbSlideToZoom = new Slider(0.2, 5.0, 1.0);
        sbSlideToZoom.setMin( 0.2);
        sbSlideToZoom.setMax( 5.0);
        sbSlideToZoom.setValue( 1.0 );
        sbSlideToZoom.setBlockIncrement(0.1);


        printSysOut("Setup slider for zooming - add listener");
       /*
            Personally I hate these embedded listener methods
            They make things really hard to read and with IntelliJ inserting
            stuff for you frequently as you edit, sometimes you can get lost
            and have to mess around endlessly to get it put back together. Sigh.
         */
        sbSlideToZoom.valueProperty().addListener((o, oldV, newV) -> {

            /*
                If we aren't using the slider, just bug out
             */
            if (!cbSlideToZoom.isSelected()) {
                return;
            }
            dZoomScale = newV.doubleValue();
            var x = spScrollPane.getHvalue();
            var y = spScrollPane.getVvalue();
            imgImageView.setFitWidth(anImage.getWidth() * dZoomScale);

            /*
                What happens if we don't restore x and y?
             */
            if (true) {
                spScrollPane.setHvalue(x);
                spScrollPane.setVvalue(y);
            }
            /*
                Report the zoom factor just for grins
             */
            printSysOut(String.format("Zoom factor %.3f", newV.doubleValue() ));
            setStatus( String.format("Zoom %.3f", dZoomScale) );
            lblSliderValue.setText( String.format("%.3f", dZoomScale));
        });


        /*
            Now set up a scroll wheel based zoom
            and set a default zoom scale
         */

        dZoomScale = 1.0;
        printSysOut("setup Wheel to zoom - ImageView Scroll Event");
        imgImageView.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        event.consume();

                        /*
                            If we are zooming with Slider, just bug out
                         */
                        if ( cbSlideToZoom.isSelected() ) {
                            return;
                        }
                        //printSysOut("Zoom with wheel");
                        double zoomFactor = 1.05;
                        double deltaY = event.getDeltaY();
                    /*
                        Don't zoom forever. Just ignore it after
                        a while.
                     */
                        double dScale = dZoomScale;
                        if (deltaY > 0.0 && dScale > 10.0) {
                            printSysOut("Don't scale too big");
                            //event.consume();
                            return;
                        } else if (deltaY < 0.0 && dScale < 0.20) {
                            printSysOut("Don't scale too small");
                            //event.consume();
                            return;
                        }

                        if (deltaY < 0) {
                            zoomFactor = 0.95;
                        }

                        var x = spScrollPane.getHvalue();
                        var y = spScrollPane.getVvalue();
                        /*
                            What happens if we don't restore x and y?
                         */
                        /*
                            Let's zoom image with setFitWidth rather than setScale
                            but save our zoom so we can report it
                         */
                        dZoomScale = dZoomScale * zoomFactor;
                        sbSlideToZoom.setValue( dZoomScale);
                        imgImageView.setFitWidth(anImage.getWidth() * zoomFactor);

                        String scaleReport = String.format("ImageView scale factor %.3f", imgImageView.getScaleX() * dZoomScale);



                        if (false) {
                            imgImageView.setScaleX(imgImageView.getScaleX() * zoomFactor);
                            imgImageView.setScaleY(imgImageView.getScaleY() * zoomFactor);
                            //String scaleReport = String.format("ImageView scale factors [%.3f, %.3f]", view.getScaleX(), view.getScaleY());
                        }

                        printSysOut(scaleReport);
                        setStatus( String.format("Zoom %.3f", dZoomScale) );
                        lblSliderValue.setText( String.format("%.3f", dZoomScale));
                        /*
                            Lets try this here and see if that fixes the pan after zoom
                         */
                        x = spScrollPane.getHvalue();
                        y = spScrollPane.getVvalue();

                        /*
                         ********************************************************************
                         *************** The following statement appears to have made it work
                         * Now wheel zooming preserves panning to the corners
                         ********************************************************************
                         */
                        imgImageView.setFitWidth(anImage.getWidth() * dZoomScale);
            /*
                What happens if we don't restore x and y?
             */
                        if (true) {
                            spScrollPane.setHvalue(x);
                            spScrollPane.setVvalue(y);
                        }
                        /*
                            we already did this above
                         */
                        if ( false ) {
                            imgImageView.setFitWidth(anImage.getWidth() * zoomFactor);
                            if (true) {
                                spScrollPane.setHvalue(x);
                                spScrollPane.setVvalue(y);
                            }
                        }
                    }
                }
        );

    } // End of onOpenImageClick

    public void SPOnMouseDragged(MouseEvent mouseEvent) {
        printSysOut("SPOnMouseDragged");
    }

    public void SPOnScroll(ScrollEvent scrollEvent) {
        printSysOut("SPOnScroll");
    }

    public void SpOnZoom(ZoomEvent zoomEvent) {
        printSysOut("SpOnZoom");
    }

    public void SpOnZoomFinished(ZoomEvent zoomEvent) {
        printSysOut("SpOnZoomFinished");
    }

    public void SpOnZoomStarted(ZoomEvent zoomEvent) {
        printSysOut("SpOnZoomStarted");
    }

    /*
        This is called
     */
    public void ImgOnMouseDragged(MouseEvent mouseEvent) {
        //printSysOut("ImgOnMouseDragged");
    }
    /*
        This is called
     */
    public void ImgOnMouseClicked(MouseEvent mouseEvent) {
        //printSysOut("ImgOnMouseClicked");
    }
}