package com.example.scientificconference.service;

import com.example.scientificconference.dto.ConferenceDto;
import com.example.scientificconference.entity.Conference;
import com.example.scientificconference.repo.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ModelMapper modelMapper;

    public Conference add(ConferenceDto conferenceDto){
        Conference conference = modelMapper.map(conferenceDto, Conference.class);

        return conferenceRepository.save(conference);
    }

    public Conference update(UUID id, ConferenceDto updateDto){
        Conference conference = modelMapper.map(updateDto, Conference.class);
        conference.setId(id);
        return conferenceRepository.save(conference);
    }

    public List<Conference> getAll(){
        return conferenceRepository.findAll();
    }
}
