package com.siemieniuk.animals.hobhw_parser;

import com.siemieniuk.animals.math.Coordinates;

import java.io.*;
import java.net.URL;

public class HobhwParser {
    private final WorldParameters params;
    private final URL src;

    public HobhwParser(URL src) {
        this.src = src;
        this.params = new WorldParameters();
    }

    public WorldParameters parse() throws FileNotFoundException {
        if (!validatePath(src.toString())) {
            throw new FileNotFoundException("Wrong file format");
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(src.openStream()));
            readDimensions(br);
            readMapContent(br);
            br.close();
        } catch (IOException e) {
            throw new FileNotFoundException("File is broken");
        }
        return params;
    }

    protected void readMapContent(BufferedReader br) throws IOException {
        int xSize = params.getxSize();
        int ySize = params.getxSize();
        for (int y=0; y<ySize; y++) {
            String line = br.readLine();
            if (line.length() != xSize) {
                throw new IOException();
            }
            for (int x=0; x<xSize; x++) {
                Coordinates c = new Coordinates(x, y);
                switch (line.charAt(x)) {
                    case '_' -> {}
                    case 'H' -> params.addHideout(c);
                    case 'W' -> params.addWaterSource(c);
                    case 'F' -> params.addPlantSource(c);
                    case 'P' -> params.addPath(c);
                    case 'I' -> params.addIntersection(c);
                    default -> throw new IOException();
                }
            }
        }
    }

    protected void readDimensions(BufferedReader br) throws IOException {
        String[] arr;
        arr = br.readLine().split(" ");
        if (arr.length != 2) {
            throw new IOException();
        }
        int xSize = Integer.parseInt(arr[0]);
        int ySize = Integer.parseInt(arr[1]);
        params.setxSize(xSize);
        params.setySize(ySize);
    }

    public static boolean validatePath(String p) {
        return p.endsWith(".hobhw");
    }
}
