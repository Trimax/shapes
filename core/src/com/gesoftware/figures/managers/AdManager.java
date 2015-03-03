package com.gesoftware.figures.managers;

import com.gesoftware.figures.services.IAdService;

public class AdManager {
    private static IAdService adService;

    public static void init() {
        if (adService != null)
            adService.setBackgroundColor(ThemesManager.getCurrentTheme().getColor().toIntBits());
    }

    public static void setAdService(IAdService adService) {
        AdManager.adService = adService;
    }

    public static IAdService getAdService() {
        return adService;
    }

    public static void setBackgroundColor(int color) {
        if (adService != null)
            adService.setBackgroundColor(color);
    }
}
