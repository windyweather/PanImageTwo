# PanImageTwo
This is a JavaFx test program to explore displaying images with 
Pan and Zoom in a ScrollPanel. 

This second iteration uses the fxml gui from PanImageTest and the code from
ImagePanZoom to try to make a working program. ImagePanZoom works, but does
not use fxml and so cannot be the basis of a more complex program.

This example works using FXML and does pan with the mouse and zoom with 
either the mouse wheel or the slider. The check box enables the slider.
The zoom factor is reported next to the slider and in the status for
both zoom methods.

Zooming is limited to the range 0.2 >> 5.0 for both methods.

The failure up to now is that for zooming beyond 1.0, panning failed
to the corners of the image. This example does that correctly for 
zoom factors all the way to 5.0.

The only failure is what happens when the zoomed image is smaller than the
viewable area. It would be nice to center the image and not allow dragging.

The work continues but this is a great success after 2 weeks of work.


