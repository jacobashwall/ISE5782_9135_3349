package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Image writer
 */
class ImageWriterTest {

    /**
     * Test method for
     * {@link renderer.ImageWriter}
     */
    @Test
    void ImageWriterTest(){
        ImageWriter imageWriter = new ImageWriter("MyFirstPicture",800,500);
        //Printing a red net over yellow background
        for(int i = 0;i < 500; i++){
            for(int j = 0; j < 800; j++) {
                //Coordinates of the net
                if(i%50 == 0 || j%50 == 0){
                    //Red
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                }
                //Background
                else {
                    //Yellow
                    imageWriter.writePixel(j, i, new Color(255,255 , 0));
                }
            }
        }

        imageWriter.writeToImage();

     }

}