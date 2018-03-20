package ru.otus.hw16.messageserver.app;

public abstract class MsgToFrontend extends Msg {
    public MsgToFrontend(String from, String to, Class<?> klass) {
        super(from, to, klass);
    }

//    @Override
//    public void exec(Addressee addressee) {
//        if (addressee instanceof FrontendService) {
//            exec((FrontendService) addressee);
//        } else {
//            //todo error!
//        }
//    }

//    public abstract void exec(FrontendService frontendService);
    public abstract void exec();
}