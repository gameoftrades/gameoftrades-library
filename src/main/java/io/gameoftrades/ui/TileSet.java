package io.gameoftrades.ui;

public enum TileSet {

    T16("/game-of-trades-16x16.png", 16, 1, 10),T32("/game-of-trades-32x32.png", 32, 2, 16), T64("/game-of-trades-64x64.png", 64, 4, 32);

    private String resource;
    private int tileSize;
    private int tileSpacing;
    private int fontSize;

    TileSet(String resource, int tileSize, int tileSpacing, int fontSize) {
        this.resource = resource;
        this.tileSize = tileSize;
        this.tileSpacing = tileSpacing;
        this.fontSize = fontSize;
    }

    public String getResource() {
        return resource;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getTileSpacing() {
        return tileSpacing;
    }

    public int getFontSize() {
        return fontSize;
    }

}
