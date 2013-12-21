package scott.kellie

import org.springframework.dao.DataIntegrityViolationException

class FamilyTests extends GroovyTestCase{

    void testCreateFamily() {
        assert !new Family(familyName: "familyName", address: 'address').validate()  //community is required
        assert !new Family(address: 'address', community: Community.first()).validate()  //familyName is required
        assert !new Family(familyName: "familyName", community: Community.first()).validate()  //address is required

        assert new Family(familyName: "familyName", address: 'address', community: Community.first()).save(flush: true)
    }

    void testUpdateFamily() {
        Family scott = Family.findByFamilyName("scott")
        scott.address = Family.findByFamilyName('robinson').address     //must be unique, there is already another family with this address
        assert !scott.validate()
        scott.address = "123 Main"
        scott.familyName = "totally_diff"
        assert scott.save(flush: true)         //saved with new  unique name and address
    }

    void testDeleteFamily() {
        Community community = Community.first()
        Family newFamily = new Family(familyName: 'name', address: 'address', community: community).save() //Family without any members

        Family.withSession { s ->
            s.flush()
            s.clear()
        }
        assert Family.exists(newFamily.id)
        newFamily.delete()
        Family.withSession { s ->
            s.flush()
            s.clear()
        }
        assert !Family.exists(newFamily.id)

        Family familyWithMembers = FamilyMember.first().family //Family with members
        assert Family.exists(familyWithMembers.id)

        try{
            familyWithMembers.delete(flush: true) //can't delete because there are families in this Family, must flush to see the effect of trying to delete within the test
            fail('Delete of this Family should fail because there are families for this Family')
        }catch (DataIntegrityViolationException e) {
            Family.withSession {s->
                s.clear()
            }
            assert Family.exists(familyWithMembers.id)
        }

    }
}
