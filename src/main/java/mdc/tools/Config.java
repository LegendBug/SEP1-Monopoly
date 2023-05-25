package mdc.tools;

public class Config {
    private Screen screen;
    private GameInfo gameInfo;
    private Rectangle rectangle;
    private ImagePath imagePath;

    public Screen getScreen() {
        return screen;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ImagePath getImagePath() {
        return imagePath;
    }

    public static class Screen {
        private int titleBar;
        private int width;
        private int height;

        public int getTitleBar() {
            return titleBar;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public static class GameInfo {
        private ActionCards actionCards;
        private MoneyCards moneyCards;
        private Properties properties;
        private RentCards rentCards;

        public ActionCards getActionCards() {
            return actionCards;
        }

        public MoneyCards getMoneyCards() {
            return moneyCards;
        }

        public Properties getProperties() {
            return properties;
        }

        public RentCards getRentCards() {
            return rentCards;
        }

        public static class ActionCards {
            private String DealBreaker;
            private String JustSayNo;
            private String SlyDeal;
            private String ForceDeal;
            private String DebtCollector;
            private String Birthday;
            private String PassGo;
            private String House;
            private String Hotel;
            private String DoubleRent;

            public String getDealBreaker() {
                return DealBreaker;
            }

            public String getJustSayNo() {
                return JustSayNo;
            }

            public String getSlyDeal() {
                return SlyDeal;
            }

            public String getForceDeal() {
                return ForceDeal;
            }

            public String getDebtCollector() {
                return DebtCollector;
            }

            public String getBirthday() {
                return Birthday;
            }

            public String getPassGo() {
                return PassGo;
            }

            public String getHouse() {
                return House;
            }

            public String getHotel() {
                return Hotel;
            }

            public String getDoubleRent() {
                return DoubleRent;
            }
        }

        public static class MoneyCards {
            private int M1;
            private int M2;
            private int M3;
            private int M4;
            private int M5;
            private int M10;

            public int getM1() {
                return M1;
            }

            public int getM2() {
                return M2;
            }

            public int getM3() {
                return M3;
            }

            public int getM4() {
                return M4;
            }

            public int getM5() {
                return M5;
            }

            public int getM10() {
                return M10;
            }
        }

        public static class Properties {
            private String darkBlue;
            private String brown;
            private String utility;
            private String green;
            private String yellow;
            private String red;
            private String orange;
            private String pink;
            private String lightBlue;
            private String railRoad;
            private String darkBlue_green;
            private String green_railRoad;
            private String utility_railRoad;
            private String lightBlue_railRoad;
            private String lightBlue_brown;
            private String pink_orange;
            private String red_yellow;
            private int multiColour;

            public String getDarkBlue() {
                return darkBlue;
            }

            public String getBrown() {
                return brown;
            }

            public String getUtility() {
                return utility;
            }

            public String getGreen() {
                return green;
            }

            public String getYellow() {
                return yellow;
            }

            public String getRed() {
                return red;
            }

            public String getOrange() {
                return orange;
            }

            public String getPink() {
                return pink;
            }

            public String getLightBlue() {
                return lightBlue;
            }

            public String getRailRoad() {
                return railRoad;
            }

            public String getDarkBlue_green() {
                return darkBlue_green;
            }

            public String getGreen_railRoad() {
                return green_railRoad;
            }

            public String getUtility_railRoad() {
                return utility_railRoad;
            }

            public String getLightBlue_railRoad() {
                return lightBlue_railRoad;
            }

            public String getLightBlue_brown() {
                return lightBlue_brown;
            }

            public String getPink_orange() {
                return pink_orange;
            }

            public String getRed_yellow() {
                return red_yellow;
            }

            public int getMultiColour() {
                return multiColour;
            }
        }

        public static class RentCards {
            private String darkBlue_green;
            private String utility_railRoad;
            private String lightBlue_brown;
            private String pink_orange;
            private String red_yellow;
            private String multiColour;

            public String getDarkBlue_green() {
                return darkBlue_green;
            }

            public String getUtility_railRoad() {
                return utility_railRoad;
            }

            public String getLightBlue_brown() {
                return lightBlue_brown;
            }

            public String getPink_orange() {
                return pink_orange;
            }

            public String getRed_yellow() {
                return red_yellow;
            }

            public String getMultiColour() {
                return multiColour;
            }
        }

    }

    public static class Rectangle {
    }

    public static class ImagePath {
        private String icon;
        private String gameStart1;
        private String gameStart2;
        private String gameStart3;
        private String background;
        private String exit1;
        private String exit2;
        private String exit3;
        private String play1;
        private String play2;
        private String play3;
        private String select1;
        private String select2;
        private String select3;
        private String history1;
        private String history2;
        private String history3;
        private String desktop;
        private String actionCard;
        private String moneyCard;
        private String propertyCard;
        private String cardBack;

        public String getCardBack() {
            return cardBack;
        }

        public String getMoneyCard() {
            return moneyCard;
        }

        public String getPropertyCard() {
            return propertyCard;
        }

        public String getDesktop() {
            return desktop;
        }

        public String getActionCard() {
            return actionCard;
        }

        public String getBackground() {
            return background;
        }

        public String getHistory1() {
            return history1;
        }

        public String getHistory2() {
            return history2;
        }

        public String getHistory3() {
            return history3;
        }

        public String getExit1() {
            return exit1;
        }

        public String getExit2() {
            return exit2;
        }

        public String getExit3() {
            return exit3;
        }

        public String getPlay1() {
            return play1;
        }

        public String getPlay2() {
            return play2;
        }

        public String getPlay3() {
            return play3;
        }

        public String getSelect1() {
            return select1;
        }

        public String getSelect2() {
            return select2;
        }

        public String getSelect3() {
            return select3;
        }

        public String getIcon() {
            return icon;
        }

        public String getGameStart1() {
            return gameStart1;
        }

        public String getGameStart2() {
            return gameStart2;
        }

        public String getGameStart3() {
            return gameStart3;
        }
    }
}
