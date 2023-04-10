package com.siemieniuk.animals.hobhw_parser;

import com.siemieniuk.animals.math.Coordinates;

import java.io.*;
import java.net.URL;

/**
 * <p>This class is used for reading HOBHW files.</p>
 * <p>How to use it?</p>
 * <ol>
 *     <li>Create a new object</li>
 *     <li>Call method <em>parse()</em></li>
 *     <li></li>
 * </ol>
 * @author Szymon Siemieniuk
 */
public class HobhwParser {
    private final WorldParameters params;
    private final URL src;

    /**
     * Recommended constructor
     * @param src A source to the file
     */
    public HobhwParser(URL src) {
        this.src = src;
        this.params = new WorldParameters();
    }

    /**
     * @return The set of parameters how the world should be created.
     * @throws FileNotFoundException
     */
    public WorldParameters parse() throws FileNotFoundException, IllegalArgumentException {
        if (!validatePath(src.toString())) {
            throw new IllegalArgumentException("Wrong file format");
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

    /**
     * Checks if the file is of the <em>.hobhw</em> extension.
     * @param path A source to the file
     * @return True if the file is valid, false otherwise
     */
    public static boolean validatePath(String path) {
        return path.endsWith(".hobhw");
    }
}
