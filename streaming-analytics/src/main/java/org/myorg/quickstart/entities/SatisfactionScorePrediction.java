package org.myorg.quickstart.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"customerId"})
public class SatisfactionScorePrediction {
    private String customerId;
    private ModelInputFeatures modelInputFeatures;
    private ModelOutputFeatures modelOutputFeatures;
}
