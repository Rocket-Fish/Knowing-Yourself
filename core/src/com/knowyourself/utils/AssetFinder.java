package com.knowyourself.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class AssetFinder {
    private AssetManager assetManager;
    private FileHandleResolver resolver;

    public class AssetDescriptor {
        public String folder;
        public Class<?> assetType;

        public AssetDescriptor(String folder, Class<?> assetType) {
            this.folder = folder;
            this.assetType = assetType;
        }
    }

    private Array<AssetDescriptor> assets = new Array<AssetDescriptor>();

    public AssetFinder(AssetManager assetManager, FileHandleResolver resolver) {
        this.assetManager = assetManager;
        this.resolver = resolver;

        assets.add(new AssetDescriptor("Characters", Texture.class));
        assets.add(new AssetDescriptor("Dialogues", Music.class));
        assets.add(new AssetDescriptor("DialogueText", String.class));
    }

    public void load() {
        for (AssetDescriptor descriptor : assets) {
            FileHandle folder = resolver.resolve("").child(descriptor.folder);
            if (!folder.exists()) {
                continue;
            }

            for (FileHandle asset : folder.list()) {
                assetManager.load(asset.path(), descriptor.assetType);
            }
        }
    }}
