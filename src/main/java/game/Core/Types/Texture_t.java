package game.Core.Types;

public class Texture_t {
    public final int width;
    public final int height;
    public final int textureId;
    public final int channels;
    public final String path;

    public Texture_t(int width, int height, int textureId, int channels, String path) {
        this.width = width;
        this.height = height;
        this.textureId = textureId;
        this.channels = channels;
        this.path = path;
    }
}
