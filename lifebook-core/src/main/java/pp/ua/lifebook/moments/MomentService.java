package pp.ua.lifebook.moments;

import pp.ua.lifebook.user.User;

public class MomentService {
    private final MomentStorage storage;

    public MomentService(MomentStorage storage) {
        this.storage = storage;
    }

    public void save(Moment moment, User user) {
        moment.setUserId(user.getId());
        storage.save(moment);
    }

    public Moment getMoment(int id) {
        return storage.getById(id);
    }
}
