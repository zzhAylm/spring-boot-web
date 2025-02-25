package com.zzh.protos;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/25 12:02
 */
@Slf4j
public class ProtobufTest {


    @Test
    public void protobufTest(){
//        Protobuf
//        PbTeamInfo.pb_team_info.var
        PersonOuterClass.Person zzh = PersonOuterClass.Person.newBuilder()
                .setName("zzh")
                .setId(1)
                .setEmail("zzh@163.com")
                .build();


        Protobuf.pb_team_info build = Protobuf.pb_team_info.newBuilder().build();
    }

}
