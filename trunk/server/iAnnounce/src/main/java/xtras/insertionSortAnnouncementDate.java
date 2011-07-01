/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xtras;

import hibernate.entities.Announcement;
import java.util.List;

/**
 *
 * @author Wasae Shoaib
 */
public class insertionSortAnnouncementDate {

    private List<Announcement> A;

    public insertionSortAnnouncementDate(List<Announcement> A) {
        this.A = A;
    }

    public List<Announcement> mySort() {
        for (int i = 1; i < A.size(); ++i) {
            Announcement key = A.get(i);
            int j = i;
            while (j > 0 && A.get(j - 1).getTtime().after(key.getTtime())) {
                A.set(j, A.get(j - 1));
                --j;
            }
            A.set(j, key);
        }
        return A;
    }
}
