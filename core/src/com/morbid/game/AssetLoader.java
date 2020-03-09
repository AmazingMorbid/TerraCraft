package com.morbid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class AssetLoader {
    private static final String ASSETS_DIR = "/core/assets/";
    private static final String TEXTURES_DIR = "textures/";

    static private HashMap<String, Texture> textures = new HashMap<>();

    public static void loadTextures() {
        try {
            Files.walk(Paths.get(getDir(TEXTURES_DIR)))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        textures.put(fileName, new Texture(TEXTURES_DIR + fileName));
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns textures located in specified texture folder
     * @param fileName - texture name with extension
     * @return texture
     */
    public static Texture getTexture(String fileName) {
        return textures.get(fileName);
    }

    public static HashMap<String, Texture> getTextures() {
        return textures;
    }

    public static void disposeTextures() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }

    private static String getDir(final String DIR) {
        return Gdx.files.getLocalStoragePath() + ASSETS_DIR + DIR;
    }
}
