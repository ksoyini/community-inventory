package scott.kellie

import org.springframework.dao.DataIntegrityViolationException


class CommunityTests extends GroovyTestCase {

    void testCreateCommunity() {
        assert !new Community().validate()  //name is required
        assert !new Community(name:" ").validate()  //name can't be blank

        assert new Community(name: 'name').save(flush: true)   //unique name is required
    }

    void testUpdateCommunity() {
        Community midlo = Community.findByName("midlo")
        midlo.name = 'sunrise'     //must be unique, there is already a community named sunrise
        assert !midlo.validate()
        midlo.name = "midlothian"
        assert midlo.save(flush: true)         //saved with a new unique name
    }

    void testDeleteCommunity() {

            Community newCommunity = new Community(name: 'name').save() //community without any families

            Community.withSession { s ->
                s.flush()
                s.clear()
            }
            assert Community.exists(newCommunity.id)
            newCommunity.delete()
            Community.withSession { s ->
                s.flush()
                s.clear()
            }
            assert !Community.exists(newCommunity.id)

            Community communityWithFamily = Family.list().first().community //community with a family
            assert Community.exists(communityWithFamily.id)

            try{
                communityWithFamily.delete(flush: true) //can't delete because there are families in this community, must flush to see the effect of trying to delete within the test
                fail('Delete of this community should fail because there are families for this community')
            }catch (DataIntegrityViolationException e) {
                Community.withSession { s ->
                    s.clear()
                }
                assert Community.exists(communityWithFamily.id)
            }

    }
}
