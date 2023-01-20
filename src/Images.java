import javax.swing.*;
import java.awt.*;

abstract class Images {
    protected void setImageFacingDown(JButton button) {
        ImageIcon facingDown = new ImageIcon("images/facingDown.png");
        Icon buttonIcon = resizeIcon(facingDown);
        button.setIcon(buttonIcon);
    }

    protected void setImage0(JButton button) {
        ImageIcon img = new ImageIcon("images/0.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage1(JButton button) {
        ImageIcon img = new ImageIcon("images/1.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage2(JButton button) {
        ImageIcon img = new ImageIcon("images/2.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage3(JButton button) {
        ImageIcon img = new ImageIcon("images/3.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage4(JButton button) {
        ImageIcon img = new ImageIcon("images/4.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage5(JButton button) {
        ImageIcon img = new ImageIcon("images/5.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage6(JButton button) {
        ImageIcon img = new ImageIcon("images/6.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage7(JButton button) {
        ImageIcon img = new ImageIcon("images/7.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImage8(JButton button) {
        ImageIcon img = new ImageIcon("images/8.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImageBomb(JButton button) {
        ImageIcon bomb = new ImageIcon("images/bomb.png");
        Icon buttonIcon = resizeIcon(bomb);
        button.setIcon(buttonIcon);
    }

    protected void setImageRedBomb(JButton button) {
        ImageIcon redBomb = new ImageIcon("images/red_bomb.png");
        Icon bombIcon = resizeIcon(redBomb);
        button.setIcon(bombIcon);
    }

    protected void setImageFlagged(JButton button) {
        ImageIcon img = new ImageIcon("images/flagged.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    protected void setImageXFlagged(JButton button) {
        ImageIcon img = new ImageIcon("images/XFlagged.png");
        Icon resImg = resizeIcon(img);
        button.setIcon(resImg);
    }

    private static Icon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(26, 26,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
