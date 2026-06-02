import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageEncryption {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            System.out.print("Enter image path: ");
            String path = sc.nextLine();

            System.out.print("Enter key: ");
            int key = sc.nextInt();

            File file = new File(path);

            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                System.out.println("Invalid image file!");
                return;
            }

            int width = image.getWidth();
            int height = image.getHeight();

            // ENCRYPT IMAGE
            BufferedImage encrypted = new BufferedImage(width, height, image.getType());

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int pixel = image.getRGB(x, y);

                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;

                    r = (r + key) % 256;
                    g = (g + key) % 256;
                    b = (b + key) % 256;

                    int newPixel = (r << 16) | (g << 8) | b;
                    encrypted.setRGB(x, y, newPixel);
                }
            }

            ImageIO.write(encrypted, "png", new File("encrypted.png"));
            System.out.println("Encrypted image saved!");

            // DECRYPT IMAGE
            BufferedImage decrypted = new BufferedImage(width, height, image.getType());

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int pixel = encrypted.getRGB(x, y);

                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;

                    r = (r - key + 256) % 256;
                    g = (g - key + 256) % 256;
                    b = (b - key + 256) % 256;

                    int newPixel = (r << 16) | (g << 8) | b;
                    decrypted.setRGB(x, y, newPixel);
                }
            }

            ImageIO.write(decrypted, "png", new File("decrypted.png"));

            // ✅ THIS LINE IS REQUIRED
            System.out.println("Decrypted image saved!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}