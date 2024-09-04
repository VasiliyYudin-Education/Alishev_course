package ru.alishev.springcourse;

import java.util.*;

public class ex4 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int processCount=scanner.nextInt();
        Map<Integer,Set<Integer>> map= new HashMap<>();
        for(int i=1;i<=processCount;++i){
            int necessaryProcessesCount=scanner.nextInt();
            if(necessaryProcessesCount!=0) {
                var set=new HashSet<Integer>(necessaryProcessesCount);
                for(int j=0;j<necessaryProcessesCount;++j){
                    set.add(scanner.nextInt());
                }
                map.put(i,set);
            }
        }
        System.out.println(bfs(map));
    }

    public static int bfs(Map<Integer,Set<Integer>> map){
//        System.out.println(map+"\n===============");
        int depth=1;
        Set<Integer> set=new HashSet(){{add(1);}};
        for(;depth<= map.size();++depth){
            Set<Integer> newSet=new HashSet<>();
            for(Integer value:set){
                if(map.containsKey(value)) {
                    newSet.addAll(map.get(value));
                }
            }
//            System.out.println(set);
//            System.out.println(newSet);
//            System.out.println("--------");
            if(newSet.isEmpty())return depth;
            set=newSet;
        }
        return depth;
    }

}
