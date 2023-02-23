package models.Scorer;

import models.vertex.IVertex;

public interface IScorer <T extends IVertex> {
    double computeCost(T from, T to);
}
