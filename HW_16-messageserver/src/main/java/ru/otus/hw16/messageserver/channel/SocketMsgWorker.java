package ru.otus.hw16.messageserver.channel;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16.messageserver.app.MsgWorker;
import ru.otus.hw16.messageserver.app.Msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tully.
 */
public class SocketMsgWorker implements MsgWorker {
    private static final Logger logger = LogManager.getLogger();
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    @Override
    public String getName() {
        return name;
    }

    private final String name;

    public SocketMsgWorker(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    @Blocks
    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                logger.info("Message send json: " + json);
                out.println(json);
                out.println();//line with json + an empty line
            }
        } catch (InterruptedException | IOException e) {
            logger.warn(e.getMessage());
        }
    }

    @Blocks
    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
//                logger.info("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    logger.info("Message received json: " + json);
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException e) {
            logger.warn(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }
}
