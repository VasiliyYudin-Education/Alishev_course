package ru.alishev.springcourse;

import java.util.*;

public class ex5 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int processCount=scanner.nextInt();
        Map<Integer, Set<Integer>> map= new HashMap<>();
        List<Integer> initArr=new ArrayList<>();
        for(int i=1;i<=processCount;++i){
            int necessaryProcessesCount=scanner.nextInt();
            if(necessaryProcessesCount!=0) {
                for(int j=0;j<necessaryProcessesCount;++j) {
                    int num = scanner.nextInt();
                    if (map.containsKey(num)) map.get(num).add(i);
                    else {
                        var set = new HashSet<Integer>();
                        set.add(i);
                        map.put(num, set);
                    }
                }
            }
            else initArr.add(i);
        }
        List<Set<Integer>> res= inversedBfs(map,initArr);
        System.out.println(res.size()+1);
        initArr.sort(Integer::compareTo);
        System.out.print(initArr.size());
        for(int num:initArr) System.out.print(" "+num);
        for(var set:res){
            System.out.println();
            System.out.print(set.size());
            for(int num:set) System.out.print(" "+num);
        }
    }

    public static List<Set<Integer>> inversedBfs(Map<Integer, Set<Integer>> map,
                                            List<Integer> initArr){
//        System.out.println("map; "+map);
//        System.out.println("arr: "+initArr);
        if(map.isEmpty())return new ArrayList<>();
        List<Set<Integer>> levels=new ArrayList<>();
        Set<Integer> set=new HashSet<>();
        for(int j=0;j<initArr.size();++j){
            set.addAll(map.get(initArr.get(j)));
        }
        levels.add(set);
//        System.out.println(set);
        for(int depth=1;depth< map.size();++depth){
            Set<Integer> newSet=new HashSet<>();
            for(Integer value:set){
                if(map.containsKey(value)) {
                    newSet.addAll(map.get(value));
                }
            }
            for(Integer value:newSet){
                for(int i=levels.size()-1;i>=0;--i){
                    levels.get(i).remove(value);
                }
            }
//            System.out.println(set);
//            System.out.println(newSet);
//            System.out.println("--------");
            if(newSet.isEmpty())return levels;
            levels.add(newSet);
            set=newSet;
        }
        return levels;
    }
}
