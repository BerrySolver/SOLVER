<template>
    <div>
        <ProfileTimetable/>
        <ProfileStatistics/>
        <ProfileHistory/>
        <ProfileMyQuestions/>
    </div>
</template>

<script>
import ProfileTimetable from "@/components/profiles/ProfileTimetable"
import ProfileStatistics from "@/components/profiles/ProfileStatistics"
import ProfileHistory from "@/components/profiles/ProfileHistory"
import ProfileMyQuestions from "@/components/profiles/ProfileMyQuestions"


export default {
    name: 'Profile',
    components: {
        ProfileTimetable,
        ProfileStatistics,
        ProfileHistory,
        ProfileMyQuestions,
    },
}
</script>

<style>

</style>