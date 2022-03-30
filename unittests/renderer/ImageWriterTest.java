package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for
     * {@link renderer.ImageWriter}
     */
    @Test
    void ImageWriterTest(){
        ImageWriter imageWriter = new ImageWriter("MyFirstPicture",800,500);
        //Printing a red net over yellow background
        for(int i = 0;i < 800; i++){
            for(int j = 0; j < 500; j++) {
                //Coordinates of the net
                if((i+1)%50 == 0 || (j+1)%50 == 0){
                    //Red
                    imageWriter.writePixel(i, j, new Color(255, 0, 0));
                }
                //Background
                else {
                    //Yellow
                    imageWriter.writePixel(i, j, new Color(255,255 , 0));
                }
            }
        }

        imageWriter.writeToImage();

     }

}