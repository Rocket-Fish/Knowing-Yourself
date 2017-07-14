package com.knowyourself.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class AssetFinder {
    private AssetManager assetManager;
    private FileHandleResolver resolver;

    public class FolderAssetDescriptor {
        public String folder;
        public Class<?> assetType;
        private boolean isText = false;

        public FolderAssetDescriptor(String folder, Class<?> assetType) {
            this.folder = folder;
            this.assetType = assetType;
        }

        public FolderAssetDescriptor(String folder, boolean isText) {
            this.folder = folder;
            this.isText = isText;
        }

        public boolean isText() {
            return isText;
        }
    }

    private Array<FolderAssetDescriptor> assets = new Array<FolderAssetDescriptor>();

    public AssetFinder(AssetManager assetManager, FileHandleResolver resolver) {
        this.assetManager = assetManager;
        this.resolver = resolver;

        assetManager.setLoader(
                Text.class,
                new TextLoader(
                        new InternalFileHandleResolver()
                )
        );

        assets.add(new FolderAssetDescriptor("Characters", Texture.class));
        assets.add(new FolderAssetDescriptor("Dialogues", Music.class));
        assets.add(new FolderAssetDescriptor("DialogueText", true));
    }

    public void load() {
        for (FolderAssetDescriptor descriptor : assets) {
            FileHandle folder = resolver.resolve("").child(descriptor.folder);
            if (!folder.exists()) {
                continue;
            }

            for (FileHandle asset : folder.list()) {
                if(descriptor.isText() == true)
                {
                    assetManager.load(new AssetDescriptor< Text >( asset.path(), Text.class, new TextLoader.TextParameter()));
                } else {
                    assetManager.load(asset.path(), descriptor.assetType);
                }
            }
        }
    }}
