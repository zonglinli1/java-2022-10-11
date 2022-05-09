import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Assignment4 {
    static volatile boolean finishedReading;

    static class Buffer {
        final Lock lock = new ReentrantLock();
        final Condition notFull  = lock.newCondition();
        final Condition notEmpty = lock.newCondition();
        final Queue<String> bufferedArea = new LinkedList<>();
        volatile int count;

        public void put(String x, boolean finished) throws InterruptedException {
            lock.lock();
            try {
                bufferedArea.offer(x);
                ++count;
                finishedReading = finished;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public String take() throws InterruptedException {
            lock.lock();
            try {
                while (!finishedReading && count == 0)
                    notEmpty.await();
                String s = bufferedArea.poll();
                --count;
                return s;
            } finally {
                lock.unlock();
            }
        }
    }

    static class ReadThread extends Thread{
        BufferedReader br;
        Buffer buffer;
        ReadThread(BufferedReader br, Buffer buffer){
            this.br = br;
            this.buffer = buffer;
        }
        @Override
        public void run(){
            try{
                String line;
                finishedReading = false;
                while ((line = br.readLine()) != null) {
                    if(line.length() >= 5){
                        buffer.put(line + " = " + eval(line), false);
                    }
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(br != null){ br.close(); }
                    buffer.put("", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class WriteThread extends Thread{
        BufferedWriter bw;
        volatile Buffer buffer;
        WriteThread(BufferedWriter bw, Buffer buffer){
            this.bw = bw;
            this.buffer = buffer;
        }
        @Override
        public void run() {
            try{
                String line;
                while(buffer.count > 0 || !finishedReading){
                    line = buffer.take();
                    bw.write(line);
                    bw.newLine();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bw != null){
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int eval(String formula){
        String[] arr = formula.split(" ");
        int sum = Integer.valueOf(arr[0]);
        for(int i = 1; i < arr.length - 1; i += 2){
            sum += Integer.valueOf(arr[i] + arr[i+1]);
        }

        return sum;
    }

    public static void main(String[] args){

      if(args.length != 2){
            System.out.println("Your program will take two input parameters: input file and output file java XXX <input file name> <output file name>");
            System.exit(1);
        }

        String inputFile = args[0]; 
        String outputFile = args[1];

        BufferedReader br = null;
        BufferedWriter bw = null;

        try{
            br = new BufferedReader(new FileReader(inputFile));
            bw = new BufferedWriter(new FileWriter(outputFile));
            Buffer buffer = new Buffer();
            Thread readThread = new ReadThread(br, buffer);
            Thread writeThread = new WriteThread(bw, buffer);
            writeThread.start();
            readThread.start();;
            writeThread.join();
            readThread.join();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null){
                    br.close();
                }
                if(bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

