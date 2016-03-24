package de.michiruf.scalor.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Observable;

/**
 * @author Michael Ruf
 * @since 2016-03-08
 */
public class Configuration extends Observable {

    @JsonProperty
    private int scanX;

    @JsonProperty
    private int scanY;

    @JsonProperty
    private int scanWidth;

    @JsonProperty
    private int scanHeight;

    @JsonProperty
    private int outputX;

    @JsonProperty
    private int outputY;

    @JsonProperty
    private int outputWidth;

    @JsonProperty
    private int outputHeight;

    public int getScanX() {
        return scanX;
    }

    public void setScanX(int scanX) {
        this.scanX = scanX;
        setChanged();
        notifyObservers();
    }

    public int getScanY() {
        return scanY;
    }

    public void setScanY(int scanY) {
        this.scanY = scanY;
        setChanged();
        notifyObservers();
    }

    public int getScanWidth() {
        return scanWidth;
    }

    public void setScanWidth(int scanWidth) {
        this.scanWidth = scanWidth;
        setChanged();
        notifyObservers();
    }

    public int getScanHeight() {
        return scanHeight;
    }

    public void setScanHeight(int scanHeight) {
        this.scanHeight = scanHeight;
        setChanged();
        notifyObservers();
    }

    public int getOutputX() {
        return outputX;
    }

    public void setOutputX(int outputX) {
        this.outputX = outputX;
        setChanged();
        notifyObservers();
    }

    public int getOutputY() {
        return outputY;
    }

    public void setOutputY(int outputY) {
        this.outputY = outputY;
        setChanged();
        notifyObservers();
    }

    public int getOutputWidth() {
        return outputWidth;
    }

    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
        setChanged();
        notifyObservers();
    }

    public int getOutputHeight() {
        return outputHeight;
    }

    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
        setChanged();
        notifyObservers();
    }
}
