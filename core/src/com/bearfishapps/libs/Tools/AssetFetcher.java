package com.bearfishapps.libs.Tools;

import com.badlogic.gdx.assets.AssetManager;

public class AssetFetcher {

    // TODO: need to fix this for large asset management
    private AssetManager assetManager;

    public AssetFetcher() {
        assetManager = new AssetManager();
    }


    public AssetManager get() {
        return assetManager;
    }

    public void clear() {
        assetManager.clear();
    }
}
