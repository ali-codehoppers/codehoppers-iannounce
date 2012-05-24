/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xtras;

import hibernate.entities.Comment;
import java.util.List;

/**
 *
 * @author Wasae Shoaib
 */
public class insertionSortCommentDate {

    private List<Comment> A;

    public insertionSortCommentDate(List<Comment> A) {
        this.A = A;
    }

    public List<Comment> mySort() {
        for (int i = 1; i < A.size(); ++i) {
            Comment key = A.get(i);
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
