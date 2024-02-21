package com.sy.RAWWAR.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionDto {
    @Id
    private String missionId;
    private String missionStatus;
    private List<String> players;
    private String startTimestamp;
    private String endTimestamp;
}
