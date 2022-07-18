import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

public class MusicPlayer extends PApplet {
    Minim minim;
    AudioPlayer audio;

    public FFT fft;

    boolean isPaused = false;

    public int blue = color(32, 57, 219);
    public int purple = color(141, 44, 156);


    public static void main(String[] args) {
        PApplet.main("MusicPlayer");
    }

    public void settings(){
        size(1000,500);

    }

    public void setup(){
        background(0,0,0);
        minim = new Minim(this);
        audio = minim.loadFile("resources/moon.mp3");
        audio.loop();
        fft = new FFT(audio.bufferSize(),audio.sampleRate());
    }

    public void draw() {
        background(0, 0, 0);

        float[] bottomChannel = audio.left.toArray();
        float[] topChannel = audio.right.toArray();
        fft.forward(audio.mix);

        for(int i = 0; i < bottomChannel.length-1; i++) {
            drawChannel(topChannel, i, blue,1);
            drawChannel(bottomChannel, i, purple,-1);
        }
        for(int i = 0; i<fft.specSize(); i++){
            drawFrequency(i);
        }
    }
    public void drawChannel(float[] channel, int index, int color, int direction){
        for(int i = 1; i < 5; i++) {

            stroke(color,(float)100/sq(i));
            line(index, height / 2, index + 1, height / 2 + abs(channel[index + 1] * 100*sq(i)) * direction);
        }
    }
    public void drawFrequency(int index){
        for(int i = 1; i < 3; i++) {
            fill(255, 255, 255, (float)100/sq(i));
            stroke(255, 255, 255, (float)100/sq(i));
            //circle(width / 2, height / 2, fft.getBand(index)); // * sq(i));
        }
    }


    public void mouseClicked(){
        isPaused = !isPaused;
        if(isPaused){
            audio.pause();
        }
        else{
            audio.play();
        }
    }
}



