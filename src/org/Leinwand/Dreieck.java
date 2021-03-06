/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Leinwand;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author ADMIN
 */
public class Dreieck extends OBJECT_2D {

    private double w, h;
    private char direction;
    private Texture textur;
    private int scalex;

    /**
     * Erstellt ein neues Dreieck
     *
     * @param x x-Koordiante des oberen linken Punkts
     * @param y y-Koordinate des oberen linken Punkts
     * @param w Breite des Dreiecks
     * @param h Höhe des Dreiecks
     */
    public Dreieck(double x, double y, double w, double h) {
        createDreieck(x, y, w, h, 'a');
    }

    /**
     * Erstellt ein neues Dreieck
     *
     * @param x x-Koordiante des oberen linken Punkts
     * @param y y-Koordinate des oberen linken Punkts
     * @param w Breite des Dreiecks
     * @param h Höhe des Dreiecks
     * @param dir Orientierung des Dreiecks
     */
    public Dreieck(double x, double y, double w, double h, char dir) {
        createDreieck(x, y, w, h, dir);
    }

    private void createDreieck(double x, double y, double w, double h, char dir) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.direction = dir;
        this.setzeFarbe("schwarz");
        this.scalex = 1;
    }

    /**
     * Setze die Breite des Dreiecks
     *
     * @param w neue Breite des Dreiecks
     */
    public void setzeW(double w) {
        this.w = w;
    }

    /**
     * Setze die Höhe des Dreiecks
     *
     * @param h neue Höhe des Dreiecks
     */
    public void setzeH(double h) {
        this.h = h;
    }

    /**
     * Gibt die Breite des Dreiecks zurück
     *
     * @return Breite des Dreiecks
     */
    public double w() {
        return this.w;
    }

    /**
     * Gibt die Höhe des Dreiecks zurück
     *
     * @return Höhe des Dreiecks
     */
    public double h() {
        return this.h;
    }

    public void zeichnen(double factor) {
        double pointx, pointy;
        if (rotation != 0) {
            transx = x + (w / 2);
            transy = y + (h / 2);
            pointx = -w / 2;
            pointy = -h / 2;

            processTranslationsOne();
        } else {
            pointx = x;
            pointy = y;
        }

        if (textur != null) {
            glColor4d(1f, 1f, 1f, 1f);
            glDisable(GL_TEXTURE_2D);
            glEnable(GL_TEXTURE_2D);
            textur.bind();
            glBegin(GL_TRIANGLES);
        } else {
            //Hier wird das Objekt gezeichnet
            glColor4d(colorrd, colorgr, colorbl, alpha);
            glBegin(GL_TRIANGLES);
        }

        double scw = this.w / this.scalex;
        double sch = this.h / this.scalex;

        int line, row;
        line = 0;
        row = 0;

        for (int scy = this.scalex; scy > 0; scy--) {
            for (int scx = this.scalex; scx > 0; scx--) {
                double scbx = pointx + ((scx - 1) * scw);
                double scby = pointy + ((scy - 1) * sch);

                if (this.direction == 'a') {
                    if (scx > scy) {
                        continue;
                    }
                    if (scx == scy) {
                        displaya(scbx, scby, scw, sch);
                    } else {
                        displayRechteck(scbx, scby, scw, sch);
                    }
                } else if (this.direction == 'b') {
                    if (scx < scalex - scy + 1) {
                        continue;
                    }
                    if (scx == scalex - scy + 1) {
                        displayb(scbx, scby, scw, sch);
                    } else {
                        displayRechteck(scbx, scby, scw, sch);
                    }
                } else if (this.direction == 'c') {
                    if (scx < scy) {
                        continue;
                    }
                    if (scx == scy) {
                        displayc(scbx, scby, scw, sch);
                    } else {
                        displayRechteck(scbx, scby, scw, sch);
                    }
                } else if (this.direction == 'd') {
                    if (scx > scalex - scy + 1) {
                        continue;
                    }
                    if (scx == scalex - scy + 1) {
                        displayd(scbx, scby, scw, sch);
                    } else {
                        displayRechteck(scbx, scby, scw, sch);
                    }
                } else {
                    if (scx > scy) {
                        continue;
                    }
                    if (scx == scy) {
                        displaya(scbx, scby, scw, sch);
                    } else {
                        displayRechteck(scbx, scby, scw, sch);
                    }
                }
            }
        }
        glEnd();
        glDisable(GL_TEXTURE_2D);

        if (rotation != 0) {
            processTranslationsTwo();
        }
    }

    /**
     * Orientierung des Dreiecks ändern
     *
     * @param turn neue Orientierung des Dreiecks
     */
    public void drehen(char turn) {
        this.direction = turn;
    }

    /**
     * Lädt eine Textur auf das Dreieck
     *
     * @param textur Pfad der zu ladenden Textur
     */
    public void ladeTextur(String textur) {
        this.textur = Leinwand.gibLeinwand().loadTexture(textur);
    }

    /**
     * Hochskalieren für Textur
     *
     * @param scale Anzahl der Abschnitte
     */
    public void scaleup(int scale) {
        this.scalex = scale;
    }

    private void displaya(double pointx, double pointy, double w, double h) {
        if (textur == null) {
            glVertex2d(pointx, pointy);
            glVertex2d(pointx, pointy + h);
            glVertex2d(pointx + w, pointy + h);
        } else {
            glTexCoord2f(0, 0);
            glVertex2d(pointx, pointy);
            glTexCoord2f(0, 1);
            glVertex2d(pointx, pointy + h);
            glTexCoord2f(1, 1);
            glVertex2d(pointx + w, pointy + h);
        }
    }

    private void displayb(double pointx, double pointy, double w, double h) {
        if (textur == null) {
            glVertex2d(pointx + w, pointy);
            glVertex2d(pointx + w, pointy + h);
            glVertex2d(pointx, pointy + h);
        } else {
            glTexCoord2f(1, 0);
            glVertex2d(pointx + w, pointy);
            glTexCoord2f(1, 1);
            glVertex2d(pointx + w, pointy + h);
            glTexCoord2f(0, 1);
            glVertex2d(pointx, pointy + h);
        }
    }

    private void displayc(double pointx, double pointy, double w, double h) {
        if (textur == null) {
            glVertex2d(pointx, pointy);
            glVertex2d(pointx + w, pointy);
            glVertex2d(pointx + w, pointy + h);
        } else {
            glTexCoord2f(0, 0);
            glVertex2d(pointx, pointy);
            glTexCoord2f(1, 0);
            glVertex2d(pointx + w, pointy);
            glTexCoord2f(1, 1);
            glVertex2d(pointx + w, pointy + h);
        }
    }

    private void displayd(double pointx, double pointy, double w, double h) {
        if (textur == null) {
            glVertex2d(pointx, pointy);
            glVertex2d(pointx, pointy + h);
            glVertex2d(pointx + w, pointy);
        } else {
            glTexCoord2f(0, 0);
            glVertex2d(pointx, pointy);
            glTexCoord2f(0, 1);
            glVertex2d(pointx, pointy + h);
            glTexCoord2f(1, 0);
            glVertex2d(pointx + w, pointy);
        }
    }

    private void displayRechteck(double scbx, double scby, double scw, double sch) {
        glTexCoord2f(1, 0);
        glVertex2d(scbx + scw, scby);
        glTexCoord2f(0, 0);
        glVertex2d(scbx, scby);
        glTexCoord2f(0, 1);
        glVertex2d(scbx, scby + sch);
        glTexCoord2f(0, 1);
        glVertex2d(scbx, scby + sch);
        glTexCoord2f(1, 1);
        glVertex2d(scbx + scw, scby + sch);
        glTexCoord2f(1, 0);
        glVertex2d(scbx + scw, scby);
    }

    //Boundingzeug
    public int bh() {
        return (int) this.h;
    }

    public int bw() {
        return (int) this.w;
    }

    public char btype() {
        return this.direction;
    }
}
