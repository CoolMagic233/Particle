package me.coolmagic233.particle.shop;

public class ShopItem {
    private String name;
    private int coins;
    private String display;

    public ShopItem(String name, int coins, String display) {
        this.name = name;
        this.coins = coins;
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
