import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICampaignAttribute, defaultValue } from 'app/shared/model/campaign-attribute.model';

export const ACTION_TYPES = {
  FETCH_CAMPAIGNATTRIBUTE_LIST: 'campaignAttribute/FETCH_CAMPAIGNATTRIBUTE_LIST',
  FETCH_CAMPAIGNATTRIBUTE: 'campaignAttribute/FETCH_CAMPAIGNATTRIBUTE',
  CREATE_CAMPAIGNATTRIBUTE: 'campaignAttribute/CREATE_CAMPAIGNATTRIBUTE',
  UPDATE_CAMPAIGNATTRIBUTE: 'campaignAttribute/UPDATE_CAMPAIGNATTRIBUTE',
  DELETE_CAMPAIGNATTRIBUTE: 'campaignAttribute/DELETE_CAMPAIGNATTRIBUTE',
  RESET: 'campaignAttribute/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICampaignAttribute>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CampaignAttributeState = Readonly<typeof initialState>;

// Reducer

export default (state: CampaignAttributeState = initialState, action): CampaignAttributeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAMPAIGNATTRIBUTE):
    case REQUEST(ACTION_TYPES.UPDATE_CAMPAIGNATTRIBUTE):
    case REQUEST(ACTION_TYPES.DELETE_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE):
    case FAILURE(ACTION_TYPES.CREATE_CAMPAIGNATTRIBUTE):
    case FAILURE(ACTION_TYPES.UPDATE_CAMPAIGNATTRIBUTE):
    case FAILURE(ACTION_TYPES.DELETE_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAMPAIGNATTRIBUTE):
    case SUCCESS(ACTION_TYPES.UPDATE_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAMPAIGNATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/campaign-attributes';

// Actions

export const getEntities: ICrudGetAllAction<ICampaignAttribute> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE_LIST,
    payload: axios.get<ICampaignAttribute>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICampaignAttribute> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNATTRIBUTE,
    payload: axios.get<ICampaignAttribute>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICampaignAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAMPAIGNATTRIBUTE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICampaignAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAMPAIGNATTRIBUTE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICampaignAttribute> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAMPAIGNATTRIBUTE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
