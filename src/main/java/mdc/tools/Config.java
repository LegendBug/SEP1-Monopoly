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
            private String green_darkBlue;
            private String green_railRoad;
            private String railRoad_utility;
            private String lightBlue_railRoad;
            private String brown_lightBlue;
            private String orange_pink;
            private String yellow_red;
            private int multiProperty;

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

            public String getGreen_darkBlue() {
                return green_darkBlue;
            }

            public String getGreen_railRoad() {
                return green_railRoad;
            }

            public String getRailRoad_utility() {
                return railRoad_utility;
            }

            public String getLightBlue_railRoad() {
                return lightBlue_railRoad;
            }

            public String getBrown_lightBlue() {
                return brown_lightBlue;
            }

            public String getOrange_pink() {
                return orange_pink;
            }

            public String getYellow_red() {
                return yellow_red;
            }

            public int getMultiProperty() {
                return multiProperty;
            }
        }

        public static class RentCards {
            private String darkBlue_green;
            private String utility_railRoad;
            private String lightBlue_brown;
            private String pink_orange;
            private String red_yellow;
            private String multiRent;

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

            public String getMultiRent() {
                return multiRent;
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
        private String discard1;
        private String discard2;
        private String discard3;
        private String select1;
        private String select2;
        private String select3;
        private String cancel1;
        private String cancel2;
        private String cancel3;
        private String history1;
        private String history2;
        private String history3;
        private String save1;
        private String save2;
        private String save3;

        private String desktop;

        private String birthday;
        private String dealBreaker;
        private String debtCollector;
        private String doubleRent;
        private String forceDeal;
        private String hotel;
        private String house;
        private String justSayNo;
        private String passGo;
        private String slyDeal;

        private String M1;
        private String M2;
        private String M3;
        private String M4;
        private String M5;
        private String M10;

        private String darkBlue_green;
        private String utility_railRoad;
        private String lightBlue_brown;
        private String pink_orange;
        private String red_yellow;
        private String multiRent;

        private String brown1;
        private String brown_lightBlue;
        private String darkBlue1;
        private String green1;
        private String green_darkBlue;
        private String green_railRoad;
        private String lightBlue1;
        private String lightBlue_railRoad;
        private String multiProperty;
        private String orange1;
        private String pink1;
        private String orange_pink;
        private String railRoad1;
        private String railRoad_utility;
        private String red1;
        private String utility1;
        private String yellow1;
        private String yellow_red;

        private String brownProperty;
        private String darkBlueProperty;
        private String greenProperty;
        private String lightBlueProperty;
        private String orangeProperty;
        private String pinkProperty;
        private String railRoadProperty;
        private String redProperty;
        private String utilityProperty;
        private String yellowProperty;

        private String cardBack;
        private String frame;

        public String getBirthday() {
            return birthday;
        }

        public String getDealBreaker() {
            return dealBreaker;
        }

        public String getDebtCollector() {
            return debtCollector;
        }

        public String getDoubleRent() {
            return doubleRent;
        }

        public String getForceDeal() {
            return forceDeal;
        }

        public String getHotel() {
            return hotel;
        }

        public String getHouse() {
            return house;
        }

        public String getJustSayNo() {
            return justSayNo;
        }

        public String getPassGo() {
            return passGo;
        }

        public String getSlyDeal() {
            return slyDeal;
        }

        public String getM1() {
            return M1;
        }

        public String getM2() {
            return M2;
        }

        public String getM3() {
            return M3;
        }

        public String getM4() {
            return M4;
        }

        public String getM5() {
            return M5;
        }

        public String getM10() {
            return M10;
        }

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

        public String getMultiRent() {
            return multiRent;
        }



        public String getCancel1() {
            return cancel1;
        }

        public String getCancel2() {
            return cancel2;
        }

        public String getCancel3() {
            return cancel3;
        }

        public String getDiscard1() {
            return discard1;
        }

        public String getDiscard2() {
            return discard2;
        }

        public String getDiscard3() {
            return discard3;
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

        public String getCardBack() {
            return cardBack;
        }

        public String getFrame() {
            return frame;
        }

        public String getDesktop() {
            return desktop;
        }

        public String getBackground() {
            return background;
        }

        public String getBrown1() {
            return brown1;
        }

        public String getBrown_lightBlue() {
            return brown_lightBlue;
        }

        public String getDarkBlue1() {
            return darkBlue1;
        }

        public String getGreen1() {
            return green1;
        }

        public String getGreen_darkBlue() {
            return green_darkBlue;
        }

        public String getGreen_railRoad() {
            return green_railRoad;
        }

        public String getLightBlue1() {
            return lightBlue1;
        }

        public String getLightBlue_railRoad() {
            return lightBlue_railRoad;
        }

        public String getMultiProperty() {
            return multiProperty;
        }

        public String getOrange1() {
            return orange1;
        }

        public String getPink1() {
            return pink1;
        }

        public String getOrange_pink() {
            return orange_pink;
        }

        public String getRailRoad1() {
            return railRoad1;
        }

        public String getRailRoad_utility() {
            return railRoad_utility;
        }

        public String getRed1() {
            return red1;
        }

        public String getUtility1() {
            return utility1;
        }

        public String getYellow1() {
            return yellow1;
        }

        public String getYellow_red() {
            return yellow_red;
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

        public String getSave1() {
            return save1;
        }

        public String getSave2() {
            return save2;
        }

        public String getSave3() {
            return save3;
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

        public String getBrownProperty() {
            return brownProperty;
        }

        public String getDarkBlueProperty() {
            return darkBlueProperty;
        }

        public String getGreenProperty() {
            return greenProperty;
        }

        public String getLightBlueProperty() {
            return lightBlueProperty;
        }

        public String getOrangeProperty() {
            return orangeProperty;
        }

        public String getPinkProperty() {
            return pinkProperty;
        }

        public String getRailRoadProperty() {
            return railRoadProperty;
        }

        public String getRedProperty() {
            return redProperty;
        }

        public String getUtilityProperty() {
            return utilityProperty;
        }

        public String getYellowProperty() {
            return yellowProperty;
        }
    }
}
