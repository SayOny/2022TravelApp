

## Option - Max_total_wal_size   

### Hypothesis   
만약 wal size의 제약이 없다면 wal이 삭제되는 속도가 느려지고 flush가 자주 일어나지 않을 것이다.   
max_total_wal_size 옵션을 사용하면 trigger되는 크기를 지정해줄 수 있다.    
그러면 wal의 size를 너무 작게했을 때 많은 flush가 일어나서 성능이 안좋아질까?   

### Design   
Independent Variable: --max_total_wal_size=[int value]   
Dependent Variable: SAF, WAF, Latency, Throughput   

./db_bench --benchmarks="fillseq" --max_total_wal_size=[0,1,10,100,1000,10000,100000,1000000,10000000, 100000000] --num=10000000   

### Experiment Enviornment   
*Server Spec   
  *OS: macOS Monterey   
  *Processor: 2.3GHz 8Core Intel Core i9   
  *SSD: APPLE SSD AP1024N 1TB   

### Result    
![r1-1](https://drive.google.com/file/d/1PYd69aCFcH0GaBVUIAKNit_4TPkdfqqI/view?usp=sharing)   
![r1-2](https://drive.google.com/file/d/12zU7vK_JZjnUA1FbO19ciofSQZPC4SkV/view?usp=sharing)   
위의 환경에서 실험했을 때는 위의 결과 사진과 같이 유의미한 결과는 없었다.   

사실 이 ```max_total_wal_size```옵션은 기본 값(default)인 0이 실제 값 0이 아니라    

```[sum of all write_buffer_size * max_write_buffer_number] * 4```로 계산이 된다.   

다음은 ```options.h```파일의 일부분이다.   
![c-1](https://drive.google.com/file/d/1VnoWgvvAfbxkFBnGIPu5Mc-oDHc8n8LQ/view?usp=sharing)   

또한 이 옵션은 2개 이상의 column family가 존재해야 효과가 있다.   

그래서 10개, 15개의 column family를 가지고 다시 실험해 보았다.    
일단 0(default)값을 계산했다.   
```
10개의 column families,   
write_buffer_size = 64 MB(Default),   
max_write_buffer_number = 2(Default)   
max_total_wal_size = [10*64MB*2]*4 = 5.12GB   
```
```
15개의 column families,   
write_buffer_size = 64 MB(Default),   
max_write_buffer_number = 2(Default)   
max_total_wal_size = [15*64MB*2]*4 = 7.68GB   
```

다음은 실험 결과이다.    
참고로 옵션을 ```./db_bench --benchmarks="fillseq" --num_column_families= --max_total_wal_size=``` 이렇게 했다.   
![r2-1](https://drive.google.com/u/1/uc?id=1zxOxRxMONuz6QLngKZIZpB74fovir4lA&export=download)   
![r2-2](https://drive.google.com/file/d/1LiBye9ubVCr5aPwKJVXzBDdoRYJzM8QF/view?usp=sharing)   
실험 시 max_total_wal_size의 범위를 너무 작게 잡으면 Too many open file이라는 코멘트와 함께 벤치마크에 실패했다.   
내 생각에는 default값에서 어느 정도 max_total_wal_size값이 작아질수록 성능이 안좋아지는 것 같다.   
그리고 column family의 값이 커지면 성능이 안좋아지는 기준의 max_total_wal_size값이 큰 것 같다.   




