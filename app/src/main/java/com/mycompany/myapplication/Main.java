/*
 * Java
 *
 * Copyright 2024-2026 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.mycompany.myapplication;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.util.Device;

public class Main {

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private static final int NIBBLE_BITS = 4;

    private static final int LOW_NIBBLE_MASK = 0x0F;

    public static void main(String[] args) {
        MicroUI.start();

        String[] lines = new String[] { "Hello World", "Architecture: " + Device.getArchitecture(),
                "Id: " + toHexString(Device.getId()) };

        Display display = Display.getDisplay();
        GraphicsContext g = display.getGraphicsContext();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        g.setColor(Colors.BLACK);
        Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

        g.setColor(Colors.WHITE);
        Font font = Font.getDefaultFont();
        int lineHeight = font.getHeight();
        int lineCount = lines.length;
        int y = (displayHeight - lineHeight * lineCount) / 2;
        for (int i = 0; i < lineCount; i++) {
            String line = lines[i];
            int x = (displayWidth - font.stringWidth(line)) / 2;
            Painter.drawString(g, line, font, x, y);
            y += lineHeight;
        }

        display.flush();
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        int size = bytes.length;
        for (int i = 0; i < size; i++) {
            int value = bytes[i] & 0xFF;
            builder.append(HEX_DIGITS[value >> NIBBLE_BITS]);
            builder.append(HEX_DIGITS[value & LOW_NIBBLE_MASK]);
        }
        return builder.toString();
    }
}
