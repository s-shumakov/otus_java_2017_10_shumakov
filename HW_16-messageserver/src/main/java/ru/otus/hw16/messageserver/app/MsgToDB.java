package ru.otus.hw16.messageserver.app;

public abstract class MsgToDB extends Msg {
    public MsgToDB(String from, String to, Class<?> klass) {
        super(from, to, klass);
    }

//    @Override
//    public void exec(Addressee addressee) {
//        if (addressee instanceof DBService) {
//            exec((DBService) addressee);
//        }
//    }

//    public abstract void exec(DBService dbService);
    public abstract void exec();
}
