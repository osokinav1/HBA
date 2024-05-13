import java.util.LinkedList;

abstract class BankCard {

    static int N = 2;

    private static long[][] massivdc = new long[N][N];

    public static long getMassivdc(long numcard) {
        long voZZn = 0;

        for(int i = 0; i < massivdc.length; i++) {
            if(massivdc[i][0] == numcard) {
                voZZn = massivdc[i][1];
            }
        }
        return voZZn;
    }

    public void setMassivdc(long numcard, long balance) {

        int BR = 0;

        for(int i = 0; i < massivdc.length; i++) {
            if(massivdc[i][0] == numcard) {
                massivdc[i][1] = balance;
            } else if(massivdc[i][0] == 0) {
                massivdc[i][0] = numcard;
                massivdc[i][1] = balance;
                BR = 1;
            }
            if(BR == 1) { break; }
            //System.out.println("Исчерпан лимит номеров");
        }

    }

    private static long[][] massivcc = new long[N][N];

    public static long getMassivcc(long numcard) {
        long voZZn = 0;

        for(int i = 0; i < massivcc.length; i++) {
            if(massivcc[i][0] == numcard) {
                voZZn = massivcc[i][1];
            }
        }
        return voZZn;
    }

    public void setMassivcc(long numcard, long balance) {

        int BR = 0;

        for(int i = 0; i < massivcc.length; i++) {
            if(massivcc[i][0] == numcard) {
                massivcc[i][1] = balance;
            } else if(massivcc[i][0] == 0) {
                massivcc[i][0] = numcard;
                massivcc[i][1] = balance;
                BR = 1;
            }
            if(BR == 1) { break; }
            //System.out.println("Исчерпан лимит номеров");
        }

    }

    class Item {
        String typecard;
        long numcard;
        long balance;
        long sumlim;

        void setType(String typecard) { this.typecard = typecard; }
        void setNumber(long numcard) {
            this.numcard = numcard;
        }
        void setBalance(long balance) { this.balance = balance; }
        void setSumlim(long sumlim) {
            this.sumlim = sumlim;
        }
    }
    public LinkedList<Item> bankcard = new LinkedList<>();


    public static void showBalanceCard(String title, LinkedList<BankCard.Item> bankcard) {
        System.out.println(title);
        for (BankCard.Item l : bankcard) {
            //System.out.println("Item: " + l);
            System.out.println("Item{" + "Тип карты = '" + l.typecard + '\'' +
                    " Номер карты = '" + l.numcard + '\'' +
                    " Кредитная часть = '" + l.sumlim + '\''+
                    " Баланс = '" + l.balance + '\''+
                    '}');
        }
        System.out.println();
    }

    public void replenishmentaccount(String typecard, long numcard, long summa, int sav005, LinkedList<BankCard.Item> bankcard) {

        if (typecard.equals("DebitCard")) {

            int i = 0;
            long newsumma;
            int NC = 0;
            if (sav005 == 1) {summa = summa + summa / 1000 * 5;}
            for (BankCard.Item l : bankcard) {
                if (l.numcard == numcard) {
                    newsumma = l.balance + summa;

                    setMassivdc(numcard, newsumma);
                    //System.out.println("Геттер равен " + getMassivdc(numcard));

                    l.setBalance(newsumma);
                    bankcard.set(i, l);
                    NC = 1;
                }
                i++;
            }
            if (NC == 0) {
                BankCard.Item item = new BankCard.Item();
                item.setType(typecard);
                item.setNumber(numcard);

                setMassivdc(numcard, summa);
                //System.out.println("Геттер равен " + getMassivdc(numcard));

                item.setBalance(summa);
                bankcard.add(item);
            }

        } else if(typecard.equals("CreditCard")) {

            int i = 0;
            long newsumma;
            int NC = 0;
            long sumlim = 10000;
            long newlim;
            long newlimost = 0;
            for (BankCard.Item l : bankcard) {
                if (l.numcard == numcard) {
                    if (l.sumlim < sumlim) {
                        newlim = l.sumlim + summa;
                        if (newlim > sumlim) {
                            newlimost = newlim - sumlim;
                            newsumma = l.balance + newlimost;

                            setMassivcc(numcard, newsumma);
                            //System.out.println("Геттер равен " + getMassivcc(numcard));

                            l.setBalance(newsumma);

                            CreditCard.setMassivclim(numcard, sumlim);
                            //System.out.println("Геттер равен " + CreditCard.getMassivclim(numcard));

                            l.setSumlim(sumlim);
                        } else if(newlim <= 10000) {
                            CreditCard.setMassivclim(numcard, newlim);
                            //System.out.println("Геттер равен " + CreditCard.getMassivclim(numcard));

                            l.setSumlim(newlim);
                        }
                    } else {
                        newsumma = l.balance + summa;

                        setMassivcc(numcard, newsumma);
                        //System.out.println("Геттер равен " + getMassivcc(numcard));

                        l.setBalance(newsumma);
                    }
                    bankcard.set(i, l);
                    NC = 1;
                }
                i++;
            }
            if (NC == 0) {
                BankCard.Item item = new BankCard.Item();
                item.setType(typecard);
                item.setNumber(numcard);

                CreditCard.setMassivclim(numcard, sumlim);
                //System.out.println("Геттер равен " + CreditCard.getMassivclim(numcard));

                item.setSumlim(sumlim);

                setMassivcc(numcard, summa);
                //System.out.println("Геттер равен " + getMassivcc(numcard));

                item.setBalance(summa);
                bankcard.add(item);
            }

        }

    }

    public boolean paythebill(String typecard, long numcard, long summa, int b1per, int pcb5, LinkedList<Item> bankcard) {

        int NC = 0;

        if (typecard.equals("DebitCard")) {

            int i = 0;
            long newsumma;
            if (b1per == 1) {summa = summa - summa / 100;}
            for (BankCard.Item l : bankcard) {
                if(l.numcard == numcard) {
                    newsumma = l.balance - summa;

                    setMassivdc(numcard, newsumma);
                    //System.out.println("Геттер равен " + getMassivdc(numcard));

                    l.setBalance(newsumma);
                    bankcard.set(i, l);
                    NC = 1;
                }
                i++;
            }
            if (NC == 0) {
                System.out.println("Списание средств невозможно");
            }

        } else if(typecard.equals("CreditCard")) {

            int i = 0;
            long newsumma;
            long credost;
            long newcredlim;
            if (pcb5 == 1 & summa > 5000) {summa = summa - summa / 100 * 5;}
            for (BankCard.Item l : bankcard) {
                if(l.numcard == numcard) {
                    if (summa <= l.balance) {
                        newsumma = l.balance - summa;

                        setMassivcc(numcard, newsumma);
                        //System.out.println("Геттер равен " + getMassivcc(numcard));

                        l.setBalance(newsumma);
                    } else {
                        credost = summa - l.balance;
                        newcredlim = l.sumlim - credost;

                        setMassivcc(numcard, 0);
                        //System.out.println("Геттер равен " + getMassivcc(numcard));

                        l.setBalance(0);

                        CreditCard.setMassivclim(numcard, newcredlim);
                        //System.out.println("Геттер равен " + CreditCard.getMassivclim(numcard));

                        l.setSumlim(newcredlim);
                    }
                    bankcard.set(i, l);
                    NC = 1;
                }
                i++;
            }
            if (NC == 0) {
                System.out.println("Списание средств невозможно");
            }

        }

        if (NC == 1) {
            return true;
        } else {
            return false;
        }

    }

}

class DebitCard extends BankCard {

}

class CreditCard extends BankCard {

    //static int N = 2;

    private static long[][] massivclim = new long[N][N];

    public static long getMassivclim(long numcard) {
        long voZZn = 0;

        for(int i = 0; i < massivclim.length; i++) {
            if(massivclim[i][0] == numcard) {
                voZZn = massivclim[i][1];
            }
        }
        return voZZn;
    }

    public static void setMassivclim(long numcard, long credlim) {

        int BR = 0;

        for(int i = 0; i < massivclim.length; i++) {
            if(massivclim[i][0] == numcard) {
                massivclim[i][1] = credlim;
            } else if(massivclim[i][0] == 0) {
                massivclim[i][0] = numcard;
                massivclim[i][1] = credlim;
                BR = 1;
            }
            if(BR == 1) { break; }
            //System.out.println("Исчерпан лимит номеров");
        }

    }

}

class DebitCard1 extends DebitCard {
    //бонусные баллы в размере 1% от покупок
}

class DebitCard2 extends DebitCard {
    //накопления в размере 0.005% от суммы пополнений
}

class CreditCard1 extends CreditCard {
    //потенциальный кешбэк 5% от покупок при условии трат больше 5000 тысяч
}

public class Bankomat {

    public static void main(String[] args) {

        DebitCard debitcards = new DebitCard();

        debitcards.replenishmentaccount("DebitCard", 1, 2000, 0, debitcards.bankcard);
        System.out.println("Тип карты = '" + "DebitCard" + '\'' + " Номер карты = '" + "1" + '\'' + " Баланс карты '" + debitcards.getMassivdc(1) + '\'');

        debitcards.replenishmentaccount("DebitCard", 2, 500, 0, debitcards.bankcard);
        System.out.println("Тип карты = '" + "DebitCard" + '\'' + " Номер карты = '" + "2" + '\'' + " Баланс карты '" + debitcards.getMassivdc(2) + '\'');

        debitcards.replenishmentaccount("DebitCard", 1, 100, 0, debitcards.bankcard);

        debitcards.paythebill("DebitCard", 1, 300, 0, 0, debitcards.bankcard);

        debitcards.showBalanceCard("Баланс карты", debitcards.bankcard);


        CreditCard creditcards = new CreditCard();

        creditcards.replenishmentaccount("CreditCard", 1, 0, 0, creditcards.bankcard);
        creditcards.replenishmentaccount("CreditCard", 1, 5000, 0, creditcards.bankcard);

        creditcards.paythebill("CreditCard", 1, 5000, 0, 0, creditcards.bankcard);

        creditcards.paythebill("CreditCard", 1, 3000, 0, 0, creditcards.bankcard);

        System.out.println("Тип карты = '" + "CreditCard" + '\'' + " Номер карты = '" + "1" + '\'' + " Баланс карты '" + creditcards.getMassivcc(1) + '\'' + " Кредитный лимит '" + creditcards.getMassivclim(1) + '\'');

        creditcards.replenishmentaccount("CreditCard", 1, 2000, 0, creditcards.bankcard);

        creditcards.replenishmentaccount("CreditCard", 1, 2000, 0, creditcards.bankcard);

        creditcards.replenishmentaccount("CreditCard", 2, 5000, 0, creditcards.bankcard);

        creditcards.showBalanceCard("Баланс карты", creditcards.bankcard);


        DebitCard1 debitcards1 = new DebitCard1();

        debitcards1.paythebill("DebitCard", 1, 5000, 1, 0, debitcards.bankcard);
        debitcards1.showBalanceCard("Баланс карты", debitcards.bankcard);

        DebitCard2 debitcards2 = new DebitCard2();

        debitcards2.replenishmentaccount("DebitCard", 1, 5000, 1, debitcards.bankcard);
        debitcards2.showBalanceCard("Баланс карты", debitcards.bankcard);

        CreditCard1 creditcards1 = new CreditCard1();

        creditcards1.paythebill("CreditCard", 1, 6000, 1, 1, creditcards.bankcard);
        creditcards1.showBalanceCard("Баланс карты", creditcards.bankcard);

    }
}
