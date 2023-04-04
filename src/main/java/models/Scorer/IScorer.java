package models.Scorer;

import models.vertex.IVertex;

// This interface intends to be developed during the next sprint
public interface IScorer <T extends IVertex> {
    double computeCost(T from, T to);
}
