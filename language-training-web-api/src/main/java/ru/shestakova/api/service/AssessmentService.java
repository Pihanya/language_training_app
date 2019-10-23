package ru.shestakova.api.service;

import java.util.Optional;
import ru.shestakova.api.model.filter.AssessmentFilter;
import ru.shestakova.api.model.text.Assessment;
import ru.shestakova.api.request.text.CreateAssessmentRequest;
import ru.shestakova.api.request.text.EditAssessmentRequest;
import ru.shestakova.api.response.text.CreateAssessmentResponse;
import ru.shestakova.api.response.text.DeleteAssessmentResponse;
import ru.shestakova.api.response.text.EditAssessmentResponse;
import ru.shestakova.api.response.text.GetAssessmentsResponse;

public interface AssessmentService {

  CreateAssessmentResponse createAssessment(Long initiatorId, CreateAssessmentRequest request);

  Optional<Assessment> findAssessmentById(Long assessmentId);

  GetAssessmentsResponse findAssessmentsByFilter(AssessmentFilter filter);

  EditAssessmentResponse editAssessment(Long initiatorId, Long assessmentId, EditAssessmentRequest request);

  DeleteAssessmentResponse deleteAssessment(Long initiatorId, Long assessmentId);
}
