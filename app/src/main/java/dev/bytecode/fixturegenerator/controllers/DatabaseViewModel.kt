package dev.bytecode.fixturegenerator.controllers

import androidx.lifecycle.*
import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team
import dev.bytecode.fixturegenerator.modals.database.FixtureRepository
import dev.bytecode.fixturegenerator.utils.generateFixture
import kotlinx.coroutines.launch

class DatabaseViewModel(private val repository: FixtureRepository): ViewModel() {

  //  private var _fixtures : MutableLiveData<List<Fixture>> = MutableLiveData()
    var fixtures: LiveData<List<Fixture>> = repository.fixtures.asLiveData()


    var teams: LiveData<List<Team>> = repository.teamList.asLiveData()


    fun addNewTeam(team: Team) {
        viewModelScope.launch {
            repository.addTeam(team)
        }
    }


    fun deleteTeam(team: Team) {
        viewModelScope.launch {
            repository.deleteTeam(team)
        }
    }

    fun updateTeam(team: Team) {
        viewModelScope.launch {
            repository.updateTeam(team)
        }
    }

    fun deleteAllTeams() {
        viewModelScope.launch {
            repository.clearTeams()
        }
    }

    fun deleteFixture() {
        viewModelScope.launch {
            repository.clearFixture()
        }
    }

    fun insertNewFixture(fixture: Fixture) {

        viewModelScope.launch {
            repository.insertNewFixture(fixture)
        }
    }

    fun updateFixture(fixture: Fixture) {

        viewModelScope.launch {
            repository.updateFixture(fixture)
        }
    }

    fun generateNewFixture() {

        viewModelScope.launch {
            teams.value?.let {
                val newFixture = generateFixture(it)
                repository.updateDatabase(newFixture)

            }
        }

    }

}


class DatabaseViewModelFactory(private val repository: FixtureRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}