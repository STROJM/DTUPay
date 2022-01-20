package g15.reporting.repositories;

import messages.reporting.Report;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Roar Nind Steffensen
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */
public class ReportRepository {

    private final Map<String, List<Report>> readModel = new ConcurrentHashMap<>();

    public void save(String userId, Report report) {
        if(!readModel.containsKey(userId)) readModel.put(userId, new LinkedList<>());
        readModel.get(userId).add(report);
    }

    public List<Report> getByUserId(String userId) {
        return readModel.getOrDefault(userId, new LinkedList<>());
    }

    public List<Report> getAll() {
        return readModel.values().stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
