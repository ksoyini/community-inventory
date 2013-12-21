package scott.kellie

import org.apache.shiro.crypto.hash.Sha256Hash


class FamilyMemberTests extends GroovyTestCase {

    void testCreateFamilyMember() {
        Family f = Family.findByFamilyName('smith')
        User u = FamilyMember.findByFamily(Family.findByFamilyName('scott')).user
        assert !new FamilyMember(family: f, user: u).validate() //can't be in > 1 family

        User niks = new User(username: 'niks', passwordHash: new Sha256Hash('niks').toHex(), roles: [Role.findByName("MEMBER_OF_HOUSEHOLD")
        ]).save()
        assert new FamilyMember(family: f, user: niks).save(flush: true) //can add new user to family
    }

    void testUpdateFamilyMember() {
        Family fam = Family.first()
        Family otherFam = Family.last()
        FamilyMember fm = FamilyMember.findByFamily(fam)
        fm.family = otherFam
        assert fm.validate() //user joins a new family, still only in one family
    }

    void testDeleteFamilyMember() {
        FamilyMember fm = FamilyMember.first()
        assert FamilyMember.exists(fm.id)
        fm.delete(flush: true)
        FamilyMember.withSession { s->
            s.clear()
        }
        assert !FamilyMember.exists(fm.id) //can delete family member as it doesn't 'belongTo' any other object
    }
}
